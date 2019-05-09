package com.kyriba.registration.domain;

public enum TicketStatus
{
  NOT_PAID("Not paid"),
  PAID("Registered"),
  CANCELLED("Cancelled"),
  REFUNDED("Refunded"),
  GRANTED("Granted");

  private final String message;


  TicketStatus(String message)
  {
    this.message = message;
  }


  public String getMessage()
  {
    return message;
  }
}
