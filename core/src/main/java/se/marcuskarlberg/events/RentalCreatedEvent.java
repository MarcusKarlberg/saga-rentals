package se.marcuskarlberg.events;

import java.time.LocalDateTime;

public class RentalCreatedEvent {
  private String rentalId;
  private String customerId;
  private String itemId;
  private Double price;
  private LocalDateTime pickupDate;
  private LocalDateTime returnDate;
  private LocalDateTime returnedAt;

  public RentalCreatedEvent() {
  }

  public RentalCreatedEvent(String rentalId, String customerId, String itemId, Double price, LocalDateTime pickupDate, LocalDateTime returnDate, LocalDateTime returnedAt) {
    this.rentalId = rentalId;
    this.customerId = customerId;
    this.itemId = itemId;
    this.price = price;
    this.pickupDate = pickupDate;
    this.returnDate = returnDate;
    this.returnedAt = returnedAt;
  }

  public LocalDateTime getPickupDate() {
    return pickupDate;
  }

  public void setPickupDate(LocalDateTime pickupDate) {
    this.pickupDate = pickupDate;
  }

  public LocalDateTime getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDateTime returnDate) {
    this.returnDate = returnDate;
  }

  public LocalDateTime getReturnedAt() {
    return returnedAt;
  }

  public void setReturnedAt(LocalDateTime returnedAt) {
    this.returnedAt = returnedAt;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getRentalId() {
    return rentalId;
  }

  public void setRentalId(String rentalId) {
    this.rentalId = rentalId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }
}
