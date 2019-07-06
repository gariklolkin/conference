package com.kyriba.submittal.service.exception;

/**
 * @author M-ABL
 */
public class ObjectNotFoundException extends RuntimeException
{
  public ObjectNotFoundException()
  {
  }


  public ObjectNotFoundException(final String message)
  {
    super(message);
  }

}
