package com.kyriba.conference.sponsorship.api.dto;

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
public class SponsorRegistrationRequest
{
  private String name;
  private String email;
}
