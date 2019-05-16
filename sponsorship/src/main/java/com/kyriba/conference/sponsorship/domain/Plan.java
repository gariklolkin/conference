package com.kyriba.conference.sponsorship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author M-ASL
 * @since v1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan
{
  private String id;
  private PlanCategory category;
  private String sponsorId;
}
