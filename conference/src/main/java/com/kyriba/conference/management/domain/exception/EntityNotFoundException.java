package com.kyriba.conference.management.domain.exception;

public class EntityNotFoundException extends RuntimeException
{
  public EntityNotFoundException(String msg)
  {
    super(msg);
  }
}
