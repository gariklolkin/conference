package com.kyriba.conference.sponsorship.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Value;


/**
 * @author M-ASL
 */
@Value
@AllArgsConstructor
public class SponsorDto
{
  private long id;
  private String name;
  private String email;
}
