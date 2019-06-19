package com.kyriba.conference.management.domain.exception;

public class TimeslotIsBooked extends RuntimeException
{
  public TimeslotIsBooked(String msg)
  {
    super(msg);
  }
}
