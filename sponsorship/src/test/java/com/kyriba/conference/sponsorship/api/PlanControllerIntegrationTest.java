package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.domain.PlanCategory;
import com.kyriba.conference.sponsorship.domain.dto.PlanDto;
import com.kyriba.conference.sponsorship.service.PlanService;
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
@WebMvcTest(PlanController.class)
@ActiveProfiles("test")
public class PlanControllerIntegrationTest
{
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PlanService planService;


  @Test
  void getPlanById_isOk() throws Exception
  {
    when(planService.readPlan(5L))
        .thenReturn(Optional.of(new PlanDto(5L, PlanCategory.GENERAL, 5L)));
    this.mockMvc.perform(get("/api/v1/plans/5")
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(5L));
  }


  @Test
  void getPlanById_isNotFound() throws Exception
  {
    when(planService.readPlan(1L))
        .thenReturn(Optional.empty());
    this.mockMvc.perform(get("/api/v1/plans/1")
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }


  @Test
  void getPlanById_isBadRequest() throws Exception
  {
    this.mockMvc.perform(get("/api/v1/plans/ID")
        .accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }
}

