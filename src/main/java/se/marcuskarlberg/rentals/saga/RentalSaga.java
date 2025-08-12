package se.marcuskarlberg.rentals.saga;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import se.marcuskarlberg.commands.ReserveItemCommand;
import se.marcuskarlberg.events.RentalCreatedEvent;
import se.marcuskarlberg.rentals.config.KafkaConfig;

/** Orchestration-based saga pattern to handle a series events and transactions in correct order */

@Slf4j
@Component
@KafkaListener(topics = "${rental.events.created.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
public class RentalSaga {
  @Qualifier("rentalCreatedKafkaTemplate")
  private KafkaTemplate<String, ReserveItemCommand> kafkaTemplate;

  private KafkaConfig kafkaConfig;

  public RentalSaga(KafkaTemplate<String, ReserveItemCommand> kafkaTemplate, KafkaConfig kafkaConfig) {
    this.kafkaTemplate = kafkaTemplate;
    this.kafkaConfig = kafkaConfig;
  }

  @KafkaHandler
  public void handleEvent(@Payload RentalCreatedEvent rentalCreatedEvent) {
    log.info(
            "Received rental event. Handling payload: CustomerId: {}. rentalId: {}. ItemId: {}",
            rentalCreatedEvent.getCustomerId(),
            rentalCreatedEvent.getRentalId(),
            rentalCreatedEvent.getItemId()
    );

    ReserveItemCommand reserveItemCommand = new ReserveItemCommand(
      rentalCreatedEvent.getRentalId(),
      rentalCreatedEvent.getItemId(),
      rentalCreatedEvent.getPickupDate(),
      rentalCreatedEvent.getReturnDate(),
      rentalCreatedEvent.getReturnedAt()
    );
    log.info(
            "Sending reserve item command: RentalId: {}. ItemId: {}",
            reserveItemCommand.getRentalId(),
            reserveItemCommand.getItemId()
    );

    kafkaTemplate.send(kafkaConfig.getRentalCommandTopic(), reserveItemCommand);
  }
}
