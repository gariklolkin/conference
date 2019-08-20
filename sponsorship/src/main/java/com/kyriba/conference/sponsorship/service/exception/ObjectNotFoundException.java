package com.kyriba.conference.sponsorship.service.exception;

/**
 * @author Aliaksandr Samal
 */
public class ObjectNotFoundException extends RuntimeException
{
  public ObjectNotFoundException(Throwable cause)
  {
    super(cause);
  }


  public ObjectNotFoundException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
