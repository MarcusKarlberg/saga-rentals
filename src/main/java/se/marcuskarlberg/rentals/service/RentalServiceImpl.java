package se.marcuskarlberg.rentals.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import se.marcuskarlberg.rentals.model.Rental;
import se.marcuskarlberg.rentals.model.RentalDTO;
import se.marcuskarlberg.rentals.model.RentalStatus;
import se.marcuskarlberg.rentals.repository.RentalRepository;
//import se.marcuskarlberg.core.RentalCreatedEvent;

import java.util.UUID;

@Slf4j
@Service
public class RentalServiceImpl implements RentalService {
  private final RentalRepository rentalRepository;

  public RentalServiceImpl(RentalRepository rentalRepository) {
    this.rentalRepository = rentalRepository;
  }

  @Override
  public RentalDTO createRentalRequest(RentalDTO rentalDTO) {
    log.info("Creating rental request {}", rentalDTO);
    String rentalRequestId = UUID.randomUUID().toString();
    rentalDTO.setRentalId(rentalRequestId);

    Rental rental = new Rental();
    BeanUtils.copyProperties(rentalDTO, rental);
    rental.setStatus(RentalStatus.CREATED);
    rentalRepository.save(rental);


    return rentalDTO;
  }
}
