package se.marcuskarlberg.rentals.service;

import se.marcuskarlberg.rentals.model.RentalDTO;

public interface RentalService {
  RentalDTO createRentalRequest(RentalDTO rentalDTO);
}
