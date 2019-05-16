package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.api.dto.SponsorRegistrationRequest;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import org.springframework.stereotype.Service;


/**
 * @author M-ASL
 * @since v1.0
 */
@Service
public class SponsorService
{
  public Sponsor registerSponsor(SponsorRegistrationRequest sponsorRegistrationRequest)
  {
    final String randomId = "123";
    return Sponsor.builder().id(randomId)
        .name(sponsorRegistrationRequest.getName())
        .email(sponsorRegistrationRequest.getEmail())
        .build();
  }
}
