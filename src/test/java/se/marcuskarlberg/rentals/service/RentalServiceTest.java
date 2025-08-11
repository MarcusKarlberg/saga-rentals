package se.marcuskarlberg.rentals.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import se.marcuskarlberg.events.RentalCreatedEvent;
import se.marcuskarlberg.rentals.config.KafkaConfig;
import se.marcuskarlberg.rentals.model.Rental;
import se.marcuskarlberg.rentals.model.RentalDTO;
import se.marcuskarlberg.rentals.repository.RentalRepository;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {
  @Mock
  RentalRepository rentalRepository;

  @Mock
  @Qualifier("rentalCreatedKafkaTemplate")
  KafkaTemplate<String, RentalCreatedEvent> kafkaTemplate;

  RentalServiceImpl rentalService;

  KafkaConfig kafkaConfig;

  @Test
  void createRentalTest() {
    this.kafkaConfig = new KafkaConfig("rental-created-events-topic", "rental-commands");
    this.rentalService = new RentalServiceImpl(
      this.rentalRepository,
      this.kafkaTemplate,
      this.kafkaConfig
    );
    RentalDTO rentalDto = createRentalDto();

    //when(rentalRepository.save(any(Rental.class))).thenReturn(RentalMapper.dtoToEntity(rentalDto));
    // Return a Rental entity with matching rentalId as service sets it
    when(rentalRepository.save(any(Rental.class))).thenAnswer(invocation -> {
      Rental rentalArg = invocation.getArgument(0);
      return rentalArg; // return the exact entity that was saved (including the rentalId set by service)
    });

    CompletableFuture<SendResult<String, RentalCreatedEvent>> future = CompletableFuture.completedFuture(mock(SendResult.class));
    when(kafkaTemplate.send(any(ProducerRecord.class))).thenReturn(future);

    RentalDTO returnedDto = rentalService.createRental(rentalDto);

    // Capture the ProducerRecord argument from kafkaTemplate.send()
    ArgumentCaptor<ProducerRecord<String, RentalCreatedEvent>> recordCaptor = ArgumentCaptor.forClass(ProducerRecord.class);

    // Verify send() called and capture the argument
    verify(kafkaTemplate, times(1)).send(recordCaptor.capture());

    ProducerRecord<String, RentalCreatedEvent> capturedRecord = recordCaptor.getValue();
    RentalCreatedEvent rentalEvent = capturedRecord.value();

    assertEquals(returnedDto.getRentalId(), rentalEvent.getRentalId());
    assertEquals(returnedDto.getItemId(), rentalEvent.getItemId());
    assertEquals(returnedDto.getQuantity(), rentalEvent.getQuantity());
    assertEquals(returnedDto.getCustomerId(), rentalEvent.getCustomerId());
  }


  private RentalDTO createRentalDto() {
    return RentalDTO.builder()
      .itemName("itemName")
      .customerId(UUID.randomUUID().toString())
      .price(99.0)
      .quantity(1)
      .itemId(UUID.randomUUID().toString())
      .pickupDate(LocalDateTime.now())
      .returnDate(LocalDateTime.now().plusDays(1))
      .returnedAt(LocalDateTime.now().plusDays(1))
      .build();
  }
}
