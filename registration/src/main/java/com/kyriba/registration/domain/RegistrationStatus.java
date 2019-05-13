package com.kyriba.registration.domain;

public enum RegistrationStatus
{
  IN_PROGRESS("In progress"),
  REGISTERED("Registered"),
  CANCELLED("Cancelled");

  private final String message;


  RegistrationStatus(String message)
  {
    this.message = message;
  }


  public String getMessage()
  {
    return message;
  }
}
