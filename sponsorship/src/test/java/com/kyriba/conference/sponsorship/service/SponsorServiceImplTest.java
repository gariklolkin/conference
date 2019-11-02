package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.dao.SponsorRepository;
import com.kyriba.conference.sponsorship.domain.EmailMessage;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Aliaksandr Samal
 */
@ExtendWith({ MockitoExtension.class })
class SponsorServiceImplTest
{
  @Mock
  private SponsorRepository sponsorRepository;
  @Mock
  private EmailClientSync emailClientSync;
  @Mock
  private EmailClientAsync emailClientAsync;

  private SponsorServiceImpl sponsorService;


  @BeforeEach
  void setUp()
  {
    sponsorService = new SponsorServiceImpl(sponsorRepository, emailClientSync, emailClientAsync);
  }


  @Test
  void createSponsor_ok()
  {
    Sponsor sponsor = new Sponsor();
    long expectedSponsorId = 6L;
    sponsor.setId(expectedSponsorId);
    when(sponsorRepository.save(notNull())).thenReturn(sponsor);

    long actualSponsorId = sponsorService.createSponsor("Name", "Email");

    assertEquals(expectedSponsorId, actualSponsorId);
    verify(emailClientAsync).sendNotification(any(EmailMessage.class));
  }


  @Test
  void createSponsor_alreadyExists()
  {
    Sponsor sponsor = new Sponsor();
    long expectedSponsorId = 5L;
    sponsor.setId(expectedSponsorId);
    //todo test not existing sponsor
//    when(sponsorRepository.save(notNull())).thenReturn(sponsor);
//
//    long actualSponsorId = sponsorService.createSponsor("Name", "Email");
//
//    assertEquals(expectedSponsorId, actualSponsorId);
//    verify(emailClient).sendNotification(any(EmailMessage.class));
  }
}
