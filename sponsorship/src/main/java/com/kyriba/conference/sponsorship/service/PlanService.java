package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.api.dto.PlanRegistrationRequest;
import com.kyriba.conference.sponsorship.domain.Plan;
import org.springframework.stereotype.Service;


/**
 * @author M-ASL
 * @since v1.0
 */
@Service
public class PlanService
{
  public Plan registerPlan(PlanRegistrationRequest sponsorRegistrationRequest)
  {
    final String randomId = "234";
    return Plan.builder().id(randomId)
        .category(sponsorRegistrationRequest.getCategory())
        .sponsorId(sponsorRegistrationRequest.getSponsorEmail())
        .build();
  }


  public String cancelPlan(String planId)
  {
    return planId;
  }
}
