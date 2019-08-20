package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.PlanCategory;
import com.kyriba.conference.sponsorship.domain.dto.PlanDto;

import java.util.Optional;


/**
 * @author Aliaksandr Samal
 */
public interface PlanService
{
  Optional<PlanDto> readPlan(long id);

  long createPlan(PlanCategory category, String sponsorEmail);

  void deletePlan(long id);
}
