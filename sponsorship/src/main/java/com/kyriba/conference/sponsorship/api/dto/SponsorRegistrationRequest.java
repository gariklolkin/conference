package com.kyriba.conference.sponsorship.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


/**
 * @author M-ASL
 * @since v1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class SponsorRegistrationRequest
{
  private @NotEmpty String name;
  private @Email String email;
}
