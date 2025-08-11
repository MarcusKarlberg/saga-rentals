package se.marcuskarlberg.events;

public class RentalCreatedEvent {
  private String rentalId;
  private String customerId;
  private String itemId;
  private Integer quantity;
  private Double price;

  public RentalCreatedEvent() {
  }

  public RentalCreatedEvent(String rentalId, String customerId, String itemId, Integer quantity, Double price) {
    this.rentalId = rentalId;
    this.customerId = customerId;
    this.itemId = itemId;
    this.quantity = quantity;
    this.price = price;
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

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
