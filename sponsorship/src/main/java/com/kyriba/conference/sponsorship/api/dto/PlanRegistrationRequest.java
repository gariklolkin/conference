package com.kyriba.conference.sponsorship.api.dto;

import com.kyriba.conference.sponsorship.domain.PlanCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


/**
 * @author M-ASL
 * @since v1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PlanRegistrationRequest
{
  private @NotNull PlanCategory category;
  private @Email String sponsorEmail;
}
