package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.domain.dto.SponsorDto;
import com.kyriba.conference.sponsorship.service.SponsorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Aliaksandr Samal
 */
@ExtendWith({ SpringExtension.class })
@WebMvcTest(SponsorController.class)
@ActiveProfiles("test")
public class SponsorControllerIntegrationTest
{
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SponsorService sponsorService;


  @Test
  void getSponsorById_isOk() throws Exception
  {
    when(sponsorService.readSponsor(5L))
        .thenReturn(Optional.of(new SponsorDto(5L, "A", "B")));
    this.mockMvc.perform(get("/api/v1/sponsors/5")
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(5L));
  }


  @Test
  void getSponsorById_isNotFound() throws Exception
  {
    when(sponsorService.readSponsor(1L))
        .thenReturn(Optional.empty());
    this.mockMvc.perform(get("/api/v1/sponsors/1")
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }


  @Test
  void getSponsorById_isBadRequest() throws Exception
  {
    this.mockMvc.perform(get("/api/v1/sponsors/ID")
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }
}
