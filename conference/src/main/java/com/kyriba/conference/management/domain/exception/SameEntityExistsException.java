package com.kyriba.conference.management.domain.exception;

public class SameEntityExistsException extends RuntimeException
{
  public SameEntityExistsException(Throwable cause)
  {
    super(cause);
  }
}
