package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.Sponsor;
import com.kyriba.conference.sponsorship.domain.dto.SponsorDto;

import javax.validation.constraints.NotBlank;
import java.util.Optional;


/**
 * @author Aliaksandr Samal
 */
public interface SponsorService
{
  long createSponsor(@NotBlank String name, @NotBlank String email);

  void sendEmailNotification(Sponsor sponsor);

  Optional<SponsorDto> readSponsor(long id);

  void deleteSponsor(long id);
}
