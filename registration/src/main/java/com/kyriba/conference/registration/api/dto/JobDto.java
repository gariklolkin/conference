package com.kyriba.conference.registration.api.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@ApiModel(description = "Request model for attendee job")
@Data
@NoArgsConstructor
public class JobDto
{
  @ApiModelProperty(value = "Company information")
  @NotBlank
  private String company;

  @ApiModelProperty(value = "Attendee job position")
  @NotBlank
  private String position;

  @ApiModelProperty(value = "Job location city")
  @NotBlank
  private String city;
}
