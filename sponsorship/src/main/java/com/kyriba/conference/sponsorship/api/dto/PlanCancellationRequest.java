package com.kyriba.conference.sponsorship.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


/**
 * @author M-ASL
 * @since v1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanCancellationRequest
{
  private @NotNull String id;
}
