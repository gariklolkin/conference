/****************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                  *
 * The content of this file is copyrighted by Kyriba Corporation            *
 * and can not be reproduced, distributed, altered or used in any form,     *
 * in whole or in part.                                                     *
 *                                                                          *
 * Date          Author         Changes                                     *
 * 2019-07-09    M-ASL          Created                                     *
 *                                                                          *
 ****************************************************************************/
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
}
