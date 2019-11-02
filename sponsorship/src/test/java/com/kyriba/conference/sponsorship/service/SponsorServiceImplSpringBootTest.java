package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.dao.SponsorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * @author Aliaksandr Samal
 */
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
@SpringBootTest(classes = { SponsorServiceImpl.class, ValidationAutoConfiguration.class })
class SponsorServiceImplSpringBootTest
{
//  @Autowired
//  MockRestServiceServer server;

  @MockBean
  private EmailClientSync emailClientSync;
  @MockBean
  private EmailClientAsync emailClientAsync;
  @MockBean
  private SponsorRepository sponsorRepository;
  @Autowired
  private SponsorService sponsorService;


  @Test
  void createSponsor_validateNotBlankParams()
  {
    assertThrows(ConstraintViolationException.class, () -> sponsorService.createSponsor(null, null));
  }
}
