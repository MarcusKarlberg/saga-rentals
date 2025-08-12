package se.marcuskarlberg.rentals.mapper;

import org.springframework.stereotype.Component;
import se.marcuskarlberg.events.RentalCreatedEvent;
import se.marcuskarlberg.rentals.model.Rental;
import se.marcuskarlberg.rentals.model.RentalDTO;

@Component
public class RentalMapper {

  public static Rental dtoToEntity(RentalDTO dto) {
    return Rental.builder()
      .customerId(dto.getCustomerId())
      .rentalId(dto.getRentalId())
      .itemName(dto.getItemName())
      .articleId(dto.getItemId())
      .price(dto.getPrice())
      .pickupDate(dto.getPickupDate())
      .returnDate(dto.getReturnDate())
      .returnedAt(dto.getReturnedAt())
      .build();
  }

  public static RentalCreatedEvent entityToEvent(Rental rentalEntity) {
    RentalCreatedEvent event = new RentalCreatedEvent();
    event.setRentalId(rentalEntity.getRentalId());
    event.setCustomerId(rentalEntity.getCustomerId());
    event.setItemId(rentalEntity.getArticleId());
    event.setPrice(rentalEntity.getPrice());
    event.setPickupDate(rentalEntity.getPickupDate());
    event.setReturnDate(rentalEntity.getReturnDate());
    event.setReturnedAt(rentalEntity.getReturnedAt());

    return event;
  }

  public static RentalDTO entityToDto(Rental savedRental) {
    return RentalDTO.builder()
      .customerId(savedRental.getCustomerId())
      .rentalId(savedRental.getRentalId())
      .itemName(savedRental.getItemName())
      .itemId(savedRental.getArticleId())
      .price(savedRental.getPrice())
      .pickupDate(savedRental.getPickupDate())
      .returnDate(savedRental.getReturnDate())
      .returnedAt(savedRental.getReturnedAt())
      .build();
  }
}

