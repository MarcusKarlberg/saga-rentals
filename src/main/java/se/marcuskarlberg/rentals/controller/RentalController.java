package se.marcuskarlberg.rentals.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.marcuskarlberg.rentals.exception.RentalException;
import se.marcuskarlberg.rentals.model.RentalDTO;
import se.marcuskarlberg.rentals.service.RentalService;

@Slf4j
@RestController
@RequestMapping("/rentals")
public class RentalController {

  private final RentalService rentalService;

  public RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  @PostMapping
  public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
    log.info("Incoming create rental request");
    try {
      RentalDTO rental = rentalService.createRental(rentalDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new RentalException("Failed to create rental request.", e.getCause());
    }
  }
}
