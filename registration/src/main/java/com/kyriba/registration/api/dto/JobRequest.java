package com.kyriba.registration.api.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description = "Request model for attendee job")
@Data
@NoArgsConstructor
public class JobRequest
{
  @ApiModelProperty(value = "Company information")
  private String company;

  @ApiModelProperty(value = "Attendee job position")
  private String position;

  @ApiModelProperty(value = "Job location city")
  private String city;
}
