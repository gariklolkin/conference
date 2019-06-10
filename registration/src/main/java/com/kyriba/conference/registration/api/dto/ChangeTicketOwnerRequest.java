package com.kyriba.conference.registration.api.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@ApiModel(description = "Request model for changing ticket owner request")
@Data
@NoArgsConstructor
public class ChangeTicketOwnerRequest
{
  @ApiModelProperty(value = "New ticket owner identity")
  @NotBlank
  private String ticketOwner;
}
