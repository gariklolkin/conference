package com.kyriba.conference.registration.api.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description = "Request model for attendee registration request")
@Data
@NoArgsConstructor
public class AttendeeRegistrationRequest
{
  @ApiModelProperty(value = "Attendee First name")
  private String firstName;

  @ApiModelProperty(value = "Attendee Last name")
  private String lastName;

  @ApiModelProperty(value = "Attendee email")
  private String email;

  @ApiModelProperty(value = "Attendee mobile phone number")
  private String mobilePhone;

  @ApiModelProperty(value = "Attendee job")
  private JobRequest job;
}
