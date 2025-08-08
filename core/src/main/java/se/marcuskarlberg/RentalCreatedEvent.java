package se.marcuskarlberg;

public class RentalCreatedEvent {
  private String rentalId;
  private String customerId;
  private String articleId;
  private Integer quantity;
  private Double price;

  public RentalCreatedEvent() {
  }

  public RentalCreatedEvent(String rentalId, String customerId, String articleId, Integer quantity, Double price) {
    this.rentalId = rentalId;
    this.customerId = customerId;
    this.articleId = articleId;
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

  public String getArticleId() {
    return articleId;
  }

  public void setArticleId(String articleId) {
    this.articleId = articleId;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
