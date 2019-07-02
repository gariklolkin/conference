package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.dao.PlanRepository;
import com.kyriba.conference.sponsorship.dao.SponsorRepository;
import com.kyriba.conference.sponsorship.domain.Plan;
import com.kyriba.conference.sponsorship.domain.PlanCategory;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import com.kyriba.conference.sponsorship.domain.dto.PlanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


/**
 * @author M-ASL
 * @since v1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService
{
  private final PlanRepository planRepository;
  private final SponsorRepository sponsorRepository;


  @Override
  public Optional<PlanDto> readPlan(long id)
  {
    return planRepository.findById(id).map(Plan::toDto);
  }


  @Override
  public long createPlan(PlanCategory category, String sponsorEmail)
  {
    Sponsor sponsor = sponsorRepository.readByEmail(sponsorEmail);
    Plan plan = new Plan();
    plan.setCategory(category);
    plan.setSponsor(sponsor);
    return planRepository.save(plan).getId();
  }


  @Override
  public void deletePlan(long id)
  {
    planRepository.deleteById(id);
  }
}
