package com.kyriba.registration.api.dto;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AttendeeTransition
{
  private String firstName;
  private String lastName;
  private String email;
  private String mobilePhone;
  private JobTransition job;
}
