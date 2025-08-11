package se.marcuskarlberg.rentals.model;

import lombok.*;

import java.time.*;

@Data
@Builder
public class RentalDTO {
  private String customerId;
  private String rentalId;
  private String itemName;
  private String itemId;
  private Integer quantity;
  private Double price;
  private LocalDateTime pickupDate;
  private LocalDateTime returnDate;
  private LocalDateTime returnedAt;
}
