package com.kyriba.conference.management.domain.exception;

public class EntityIsUsedException extends RuntimeException
{
  public EntityIsUsedException(Throwable cause)
  {
    super(cause);
  }
}
