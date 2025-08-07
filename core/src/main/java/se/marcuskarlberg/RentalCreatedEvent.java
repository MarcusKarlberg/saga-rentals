package se.marcuskarlberg;

import lombok.*;

@Data
@Builder
public class RentalCreatedEvent {
  private String rentalId;
  private String customerId;
  private String articleId;
  private Integer quantity;
}
