/****************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                  *
 * The content of this file is copyrighted by Kyriba Corporation            *
 * and can not be reproduced, distributed, altered or used in any form,     *
 * in whole or in part.                                                     *
 *                                                                          *
 * Date          Author         Changes                                     *
 * 2019-05-13    M-ASL          Created                                     *
 *                                                                          *
 ****************************************************************************/
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
