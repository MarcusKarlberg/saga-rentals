package se.marcuskarlberg.rentals.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.marcuskarlberg.rentals.exception.RentalException;
import se.marcuskarlberg.rentals.model.RentalDTO;
import se.marcuskarlberg.rentals.service.RentalService;

@RestController
@RequestMapping("/rentals")
public class RentalController {

  private final RentalService rentalService;

  public RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  @PostMapping
  public ResponseEntity<RentalDTO> createOrder(@RequestBody RentalDTO rentalDTO) {
    try {
      RentalDTO rental = rentalService.createRentalRequest(rentalDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    } catch (Exception e) {
      throw new RentalException("Failed to create rental request.", e.getCause());
    }
  }
}
