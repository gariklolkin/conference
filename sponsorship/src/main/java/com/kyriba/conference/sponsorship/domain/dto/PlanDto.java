package com.kyriba.conference.sponsorship.domain.dto;

import com.kyriba.conference.sponsorship.domain.PlanCategory;
import lombok.AllArgsConstructor;
import lombok.Value;


/**
 * @author M-ASL
 */
@Value
@AllArgsConstructor
public class PlanDto
{
  private long id;
  private PlanCategory category;
  private Long sponsorId;
}
