package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.api.dto.PlanRegistrationRequest;
import com.kyriba.conference.sponsorship.domain.Plan;
import com.kyriba.conference.sponsorship.domain.PlanCategory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;


/**
 * @author M-ASL
 * @since v1.0
 */
@Service
public class PlanService
{
  public Collection<Plan> getAvailablePackagesPackages()
  {
    return null;
  }


  public Map<PlanCategory, Integer> getAvailablePackagesPackageTypes()
  {
    return null;
  }


  public Plan registerPlan(PlanRegistrationRequest sponsorRegistrationRequest)
  {
    final String randomId = "234";
    return Plan.builder().id(randomId)
        .category(sponsorRegistrationRequest.getCategory())
        .sponsorId(sponsorRegistrationRequest.getSponsorId())
        .build();
  }


  public String cancelPlan(String planId)
  {
    return planId;
  }
}
