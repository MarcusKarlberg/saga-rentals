package se.marcuskarlberg.rentals.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.marcuskarlberg.rentals.model.RentalDTO;

import java.util.UUID;

@Slf4j
@Service
public class RentalServiceImpl implements RentalService {
  @Override
  public RentalDTO createRentalRequest(RentalDTO rentalDTO) {
    log.info("Creating rental request {}", rentalDTO);
    String rentalRequestId = UUID.randomUUID().toString();
    rentalDTO.setId(rentalRequestId);
    //TODO: save and publish rental request to kafka

    return rentalDTO;
  }
}
