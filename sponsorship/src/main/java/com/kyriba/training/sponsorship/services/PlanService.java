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
package com.kyriba.training.sponsorship.services;

import com.kyriba.training.sponsorship.api.dto.PlanCategory;
import com.kyriba.training.sponsorship.api.dto.PlanRegistrationRequest;
import com.kyriba.training.sponsorship.domain.Plan;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;


/**
 * @author M-ASL
 * @since 19.2
 */
@Service
public class PlanService
{
  public Collection<Plan> getAvailablePackagesPackages()
  {
    return null;
  }


  public Map<PlanCategory, Integer> getAvailablePackagesPackageTypes()
  {
    return null;
  }


  public Plan registerPlan(PlanRegistrationRequest sponsorRegistrationRequest)
  {
    final String randomId = "234";
    return Plan.builder().id(randomId)
        .category(sponsorRegistrationRequest.getCategory())
        .sponsorId(sponsorRegistrationRequest.getSponsorId())
        .build();
  }


  public String cancelPlan(String planId)
  {
    return planId;
  }
}
