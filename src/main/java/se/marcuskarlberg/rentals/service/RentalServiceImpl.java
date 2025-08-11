package se.marcuskarlberg.rentals.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import se.marcuskarlberg.events.RentalCreatedEvent;
import se.marcuskarlberg.rentals.config.KafkaConfig;
import se.marcuskarlberg.rentals.mapper.RentalMapper;
import se.marcuskarlberg.rentals.model.*;
import se.marcuskarlberg.rentals.repository.RentalRepository;

import java.util.UUID;

@Slf4j
@Service
public class RentalServiceImpl implements RentalService {
  public static final String MESSAGE_ID = "messageId";
  private final RentalRepository rentalRepository;
  @Qualifier("rentalCreatedKafkaTemplate")
  private final KafkaTemplate<String, RentalCreatedEvent> kafkaTemplate;
  private final KafkaConfig kafkaConfig;

  public RentalServiceImpl(RentalRepository rentalRepository, KafkaTemplate<String, RentalCreatedEvent> kafkaTemplate, KafkaConfig kafkaConfig) {
    this.rentalRepository = rentalRepository;
    this.kafkaTemplate = kafkaTemplate;
    this.kafkaConfig = kafkaConfig;
  }

  @Override
  public RentalDTO createRental(RentalDTO rentalDTO) {
    log.info("Creating rental request {}", rentalDTO);
    String rentalRequestId = UUID.randomUUID().toString();
    rentalDTO.setRentalId(rentalRequestId);

    Rental rental = RentalMapper.dtoToEntity(rentalDTO);
    rental.setStatus(RentalStatus.CREATED);
    Rental savedRental = rentalRepository.save(rental);
    log.info("Rental request saved to sb: {}", savedRental);

    RentalCreatedEvent rentalCreatedEvent = RentalMapper.entityToEvent(savedRental);
    String messageId = UUID.randomUUID().toString();
    ProducerRecord<String, RentalCreatedEvent> record =
      new ProducerRecord<>(kafkaConfig.getRentalCreatedTopic(), rentalRequestId, rentalCreatedEvent);
    record.headers().add(MESSAGE_ID, messageId.getBytes());

    log.info("Sending rental request record: {}", record);
    kafkaTemplate.send(record);

    return RentalMapper.entityToDto(savedRental);
  }
}
