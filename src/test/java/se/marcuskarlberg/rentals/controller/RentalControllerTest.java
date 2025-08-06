package se.marcuskarlberg.rentals.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import se.marcuskarlberg.rentals.model.RentalDTO;
import se.marcuskarlberg.rentals.service.RentalService;

import java.util.UUID;

@WebMvcTest(RentalController.class)
public class RentalControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Mock
  private RentalService rentalService;

  @Test
  void createRentalRequestTest() throws Exception {
    RentalDTO rentalDTO = RentalDTO.builder()
      .articleId(UUID.randomUUID().toString())
      .itemName("Iphone")
      .price(99.0)
      .quantity(1)
      .build();
  }
}
