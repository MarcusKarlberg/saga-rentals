package se.marcuskarlberg.rentals.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import se.marcuskarlberg.rentals.model.RentalDTO;
import se.marcuskarlberg.rentals.service.RentalService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RentalController.class)
public class RentalControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RentalService rentalService;

  @Test
  void createRentalRequestTest() throws Exception {
    RentalDTO rentalDTO = RentalDTO.builder()
      .articleId(UUID.randomUUID().toString())
      .itemName("Iphone")
      .price(99.0)
      .quantity(1)
      .build();

    when(rentalService.createRentalRequest(any(RentalDTO.class))).thenReturn(rentalDTO);

    mockMvc.perform(post("/rentals")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(rentalDTO)))
      .andExpect(status().isCreated())
      .andExpect(content().json(new ObjectMapper().writeValueAsString(rentalDTO)));
  }
}
