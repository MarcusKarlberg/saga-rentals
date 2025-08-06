package se.marcuskarlberg.rentals.model;

import lombok.*;

import java.time.*;

@Data
@Builder
public class RentalDTO {
  private String id;
  private String itemName;
  private String articleId;
  private Integer quantity;
  private Double price;
  private LocalDateTime pickupDate;
  private LocalDateTime returnDate;
  private LocalDateTime returnedAt;
}
