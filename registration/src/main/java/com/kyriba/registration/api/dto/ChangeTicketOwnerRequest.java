package com.kyriba.registration.api.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description = "Request model for changing ticket owner request")
@Data
@NoArgsConstructor
public class ChangeTicketOwnerRequest
{
  @ApiModelProperty(value = "New ticket owner identity")
  private String ticketOwner;
}
