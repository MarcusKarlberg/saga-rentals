package se.marcuskarlberg.rentals.exception;

public class NotRetriableException extends RuntimeException {
  public NotRetriableException(String message) {
    super(message);
  }
}
