package se.marcuskarlberg.rentals.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import se.marcuskarlberg.RentalCreatedEvent;
import se.marcuskarlberg.rentals.model.*;
import se.marcuskarlberg.rentals.repository.RentalRepository;

import java.util.UUID;

import static se.marcuskarlberg.rentals.config.KafkaConfig.RENTAL_CREATED_TOPIC;

@Slf4j
@Service
public class RentalServiceImpl implements RentalService {
  private final RentalRepository rentalRepository;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public RentalServiceImpl(RentalRepository rentalRepository, KafkaTemplate<String, Object> kafkaTemplate) {
    this.rentalRepository = rentalRepository;
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public RentalDTO createRentalRequest(RentalDTO rentalDTO) {
    log.info("Creating rental request {}", rentalDTO);
    String rentalRequestId = UUID.randomUUID().toString();
    rentalDTO.setRentalId(rentalRequestId);

    Rental rental = new Rental();
    BeanUtils.copyProperties(rentalDTO, rental);
    rental.setStatus(RentalStatus.CREATED);
    rentalRepository.save(rental);

    RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
    BeanUtils.copyProperties(rentalDTO, rentalCreatedEvent);
    kafkaTemplate.send(RENTAL_CREATED_TOPIC, rentalCreatedEvent);

    return rentalDTO;
  }
}
