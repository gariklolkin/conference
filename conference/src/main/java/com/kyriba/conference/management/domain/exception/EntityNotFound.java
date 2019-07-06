package com.kyriba.conference.management.domain.exception;

public class EntityNotFound extends RuntimeException
{
  public EntityNotFound(String msg)
  {
    super(msg);
  }
}
