package com.kyriba.conference.registration.api.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@ApiModel(description = "Request model for attendee registration request")
@Data
@NoArgsConstructor
public class AttendeeRegistrationRequest
{
  @ApiModelProperty(value = "Attendee First name")
  @NotBlank
  private String firstName;

  @ApiModelProperty(value = "Attendee Last name")
  @NotBlank
  private String lastName;

  @ApiModelProperty(value = "Attendee email")
  @NotBlank
  private String email;

  @ApiModelProperty(value = "Attendee mobile phone number")
  @NotBlank
  private String mobilePhone;

  @ApiModelProperty(value = "Attendee job")
  @NotNull(message = "Attendee job is mandatory")
  private JobDto job;
}
