package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.dao.SponsorRepository;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import com.kyriba.conference.sponsorship.domain.dto.SponsorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.Optional;


/**
 * @author M-ASL
 * @since v1.0
 */
@Service
@Transactional
@RequiredArgsConstructor
@Validated // it works, but may be it should be moved to interface?
public class SponsorServiceImpl implements SponsorService
{
  private final SponsorRepository sponsorRepository;
  private final EmailClient emailClient;


  @Override
  public long createSponsor(@NotBlank String name, @NotBlank String email)
  {
    Sponsor sponsor = new Sponsor();
    sponsor.setName(name);
    sponsor.setEmail(email);
    emailClient.sendNotification(sponsor.toEmailMessage());
    return sponsorRepository.save(sponsor).getId();
  }


  @Override
  public Optional<SponsorDto> readSponsor(long id)
  {
    return sponsorRepository.findById(id).map(Sponsor::toDto);
  }


  @Override
  public void deleteSponsor(long id)
  {
    sponsorRepository.deleteById(id);
  }
}
