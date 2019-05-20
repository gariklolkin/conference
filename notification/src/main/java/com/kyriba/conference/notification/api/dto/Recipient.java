package com.kyriba.conference.notification.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description="A pair of a recipient name and address")
public class Recipient
{
  @ApiModelProperty(required = true, value = "Recipients name")
  @NotBlank
  private String name;

  @ApiModelProperty(required = true, value = "Recipients address or a telephone number")
  @NotBlank
  private String address;
}
