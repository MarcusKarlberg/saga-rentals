package se.marcuskarlberg.rentals.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Rental implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String rentalId;

  @Column(nullable = false)
  private String customerId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RentalStatus status;

  @Column(nullable = false)
  private String itemName;

  @Column(nullable = false)
  private String articleId;

  @Column(nullable = false)
  private Integer quantity;

  @Column(nullable = false)
  private Double price;

  @Column(nullable = false)
  private LocalDateTime pickupDate;

  @Column(nullable = false)
  private LocalDateTime returnDate;

  @Column
  private LocalDateTime returnedAt;
}
