package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.dao.SponsorRepository;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.StreamSupport;


/**
 * @author M-ASL
 * @since v1.0
 */
@Service
@Transactional
public class SponsorService
{
  private final SponsorRepository sponsorRepository;


  public SponsorService(SponsorRepository sponsorRepository)
  {
    this.sponsorRepository = sponsorRepository;
  }


  public Sponsor createSponsor(String name, String email)
  {
    Sponsor sponsor = Sponsor.builder()
        .name(name)
        .email(email)
        .build();
    return sponsorRepository.save(sponsor);
  }


  @Nullable
  public Sponsor readByEmail(String email)
  {
    return StreamSupport.stream(sponsorRepository.findAll().spliterator(), false)
        .filter(x -> email.equals(x.getEmail()))
        .findFirst()
        .orElse(null);
  }


  public EmailMessage createSponsorEmailMessage(Sponsor sponsor)
  {
    Recipient recipient = Recipient.builder()
        .name(sponsor.getName())
        .address(sponsor.getEmail())
        .build();
    return EmailMessage.builder()
        .recipients(Arrays.asList(recipient))
        .emailMessageOptions(new HashSet<>(Arrays.asList(EmailMessage.Options.HIGH_PRIORITY)))
        .build();
  }
}
