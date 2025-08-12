package se.marcuskarlberg.commands;

import java.time.LocalDateTime;

public class ReserveItemCommand {
  private String rentalId;
  private String itemId;
  private LocalDateTime pickupDate;
  private LocalDateTime returnDate;
  private LocalDateTime returnedAt;

  public ReserveItemCommand() {
  }

  public ReserveItemCommand(String rentalId, String itemId, LocalDateTime pickupDate, LocalDateTime returnDate, LocalDateTime returnedAt) {
    this.rentalId = rentalId;
    this.itemId = itemId;
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

  public String getRentalId() {
    return rentalId;
  }

  public void setRentalId(String rentalId) {
    this.rentalId = rentalId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }
}
