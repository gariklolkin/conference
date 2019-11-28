package com.kyriba.conference.management.api;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;


@ControllerAdvice
public class GlobalExceptionHandler
{
  @ExceptionHandler(ConstraintViolationException.class)
  void handleException(HttpServletResponse response, RuntimeException e) throws IOException
  {
    response.sendError(BAD_REQUEST.value(), e.getMessage());
  }
}
