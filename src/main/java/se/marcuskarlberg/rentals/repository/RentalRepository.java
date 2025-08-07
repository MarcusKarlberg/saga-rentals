package se.marcuskarlberg.rentals.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.marcuskarlberg.rentals.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}
