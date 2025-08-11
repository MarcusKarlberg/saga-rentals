package se.marcuskarlberg.commands;

public class ReserveItemCommand {
  private String rentalId;
  private String itemId;
  private Integer quantity;

  public ReserveItemCommand() {
  }

  public ReserveItemCommand(String rentalId, String itemId, Integer quantity) {
    this.rentalId = rentalId;
    this.itemId = itemId;
    this.quantity = quantity;
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

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
