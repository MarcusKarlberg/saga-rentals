package se.marcuskarlberg.rentals.exception;

public class RetriableException extends RuntimeException {
  public RetriableException(String message) {
    super(message);
  }
}
