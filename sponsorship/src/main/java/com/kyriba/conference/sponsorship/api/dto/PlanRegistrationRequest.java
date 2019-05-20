package com.kyriba.conference.sponsorship.api.dto;

import com.kyriba.conference.sponsorship.domain.PlanCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author M-ASL
 * @since v1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanRegistrationRequest
{
  private PlanCategory category;
  private String sponsorId;
}
