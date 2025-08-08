package se.marcuskarlberg.rentals.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import se.marcuskarlberg.RentalCreatedEvent;
import se.marcuskarlberg.rentals.mapper.RentalMapper;
import se.marcuskarlberg.rentals.model.*;
import se.marcuskarlberg.rentals.repository.RentalRepository;

import java.util.UUID;

import static se.marcuskarlberg.rentals.config.KafkaConfig.RENTAL_CREATED_TOPIC;

@Slf4j
@Service
public class RentalServiceImpl implements RentalService {
  public static final String MESSAGE_ID = "messageId";
  private final RentalRepository rentalRepository;
  private final KafkaTemplate<String, RentalCreatedEvent> kafkaTemplate;

  public RentalServiceImpl(RentalRepository rentalRepository, KafkaTemplate<String, RentalCreatedEvent> kafkaTemplate) {
    this.rentalRepository = rentalRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public RentalDTO createRentalRequest(RentalDTO rentalDTO) {
    log.info("Creating rental request {}", rentalDTO);
    String rentalRequestId = UUID.randomUUID().toString();
    rentalDTO.setRentalId(rentalRequestId);

    Rental rental = RentalMapper.dtoToEntity(rentalDTO);
    rental.setStatus(RentalStatus.CREATED);
    Rental savedRental = rentalRepository.save(rental);

    RentalCreatedEvent rentalCreatedEvent = RentalMapper.entityToEvent(savedRental);
    String messageId = UUID.randomUUID().toString();
    ProducerRecord<String, RentalCreatedEvent> record =
      new ProducerRecord<>(RENTAL_CREATED_TOPIC, rentalRequestId, rentalCreatedEvent);
    record.headers().add(MESSAGE_ID, messageId.getBytes());

    kafkaTemplate.send(record);

    return RentalMapper.entityToDto(savedRental);
  }
}
