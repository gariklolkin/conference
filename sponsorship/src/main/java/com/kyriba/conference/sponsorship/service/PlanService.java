package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.dao.PlanRepository;
import com.kyriba.conference.sponsorship.domain.Plan;
import com.kyriba.conference.sponsorship.domain.PlanCategory;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/**
 * @author M-ASL
 * @since v1.0
 */
@Service
@Transactional
public class PlanService
{
  private final PlanRepository planRepository;


  public PlanService(PlanRepository planRepository)
  {
    this.planRepository = planRepository;
  }


  @Nullable
  public Plan readPlan(Long id)
  {
    return planRepository.findById(id).orElse(null);
  }


  public Plan createPlan(PlanCategory category, Sponsor sponsor)
  {
    Long sponsorId = sponsor == null ? null : sponsor.getId();
    final Plan plan = Plan.builder()
        .category(category)
        .sponsorId(sponsorId)
        .build();
    return planRepository.save(plan);
  }


  public void deletePlan(Long id)
  {
    planRepository.deleteById(id);
  }
}
