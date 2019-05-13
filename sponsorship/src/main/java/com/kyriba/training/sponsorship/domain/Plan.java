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
package com.kyriba.training.sponsorship.domain;

import com.kyriba.training.sponsorship.api.dto.PlanCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author M-ASL
 * @since 19.2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan
{
  private String id;
  private PlanCategory category;
  private String sponsorId;
}
