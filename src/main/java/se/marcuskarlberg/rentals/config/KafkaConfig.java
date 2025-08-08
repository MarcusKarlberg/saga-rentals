package se.marcuskarlberg.rentals.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import se.marcuskarlberg.RentalCreatedEvent;

import java.util.Map;

@Configuration
public class KafkaConfig {

  public static final String RENTAL_CREATED_TOPIC = "rental-created-events-topic" ;

  @Bean
  KafkaTemplate<String, RentalCreatedEvent> kafkaTemplate(ProducerFactory<String, RentalCreatedEvent> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public NewTopic createRentalCreatedTopic() {
    return TopicBuilder.name(RENTAL_CREATED_TOPIC)
      .partitions(3)
      .replicas(1)
      .configs(Map.of("min.insync.replicas", "2"))
      .build();
  }

  //TODO: set configs
}
