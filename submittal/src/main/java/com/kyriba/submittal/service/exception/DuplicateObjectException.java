package com.kyriba.submittal.service.exception;

/**
 * @author M-ABL
 */
public class DuplicateObjectException extends RuntimeException
{
  public DuplicateObjectException()
  {
  }


  public DuplicateObjectException(final String message)
  {
    super(message);
  }
}
