package se.marcuskarlberg.rentals.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;
import se.marcuskarlberg.commands.ReserveItemCommand;
import se.marcuskarlberg.events.RentalCreatedEvent;
import se.marcuskarlberg.rentals.exception.NotRetriableException;
import se.marcuskarlberg.rentals.exception.RetriableException;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

  private final String rentalCreatedTopic;
  private final String rentalCommandTopic;

  public KafkaConfig(
    @Value("${rental.events.created.topic.name}") String rentalCreatedTopic,
    @Value("${rental.commands.topic.name}") String rentalCommandTopic
  ) {
    this.rentalCreatedTopic = rentalCreatedTopic;
    this.rentalCommandTopic = rentalCommandTopic;
  }

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.consumer.group-id}")
  private String consumerGroupId;

  @Value("${spring.kafka.consumer.auto-offset-reset:earliest}")
  private String autoOffsetReset;

  @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages:*}")
  private String trustedPackages;

  @Bean
  public <T> ProducerFactory<String, T> producerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    config.put(ProducerConfig.ACKS_CONFIG, "all");
    config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
    config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);
    config.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
    return new DefaultKafkaProducerFactory<>(config);
  }

  @Bean("reserveItemKafkaTemplate")
  public KafkaTemplate<String, ReserveItemCommand> reserveItemKafkaTemplate(
    ProducerFactory<String, ReserveItemCommand> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean("rentalCreatedKafkaTemplate")
  KafkaTemplate<String, RentalCreatedEvent> rentalCreatedKafkaTemplate(
    ProducerFactory<String, RentalCreatedEvent> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
    config.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
    return new DefaultKafkaConsumerFactory<>(config);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
    ConsumerFactory<String, Object> consumerFactory,
    KafkaTemplate<String, Object> kafkaTemplate) {

    DefaultErrorHandler errorHandler = new DefaultErrorHandler(
      new DeadLetterPublishingRecoverer(kafkaTemplate),
      new FixedBackOff(5000L, 3)
    );
    errorHandler.addNotRetryableExceptions(NotRetriableException.class);
    errorHandler.addRetryableExceptions(RetriableException.class);

    ConcurrentKafkaListenerContainerFactory<String, Object> factory =
      new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory);
    factory.setCommonErrorHandler(errorHandler);

    return factory;
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public NewTopic createRentalCreatedTopic() {
    return TopicBuilder.name(rentalCreatedTopic)
      .partitions(3)
      .replicas(1) // change to 2+ for prod clusters
      .configs(Map.of("min.insync.replicas", "1"))
      .build();
  }

  @Bean
  public NewTopic createRentalCommandTopic() {
    return TopicBuilder.name(rentalCommandTopic)
      .partitions(3)
      .replicas(1)
      .configs(Map.of("min.insync.replicas", "1"))
      .build();
  }

  public String getRentalCreatedTopic() {
    return rentalCreatedTopic;
  }

  public String getRentalCommandTopic() {
    return rentalCommandTopic;
  }
}
