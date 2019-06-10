package com.kyriba.conference.registration.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum RegistrationStatus
{
  IN_PROGRESS("In progress"),
  REGISTERED("Registered"),
  CANCELLED("Cancelled");

  private final String message;
}
