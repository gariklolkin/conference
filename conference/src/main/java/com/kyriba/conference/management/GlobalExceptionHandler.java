package com.kyriba.conference.management;


import com.kyriba.conference.management.domain.exception.EntityNotFound;
import com.kyriba.conference.management.domain.exception.InvalidPresentationTime;
import com.kyriba.conference.management.domain.exception.LinkedEntityNotFound;
import com.kyriba.conference.management.domain.exception.TimeslotIsBooked;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@ControllerAdvice
public class GlobalExceptionHandler
{
  @ExceptionHandler({ EntityNotFound.class })
  public final ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFound exception)
  {
    return getError(exception.getMessage(), NOT_FOUND);
  }


  @ExceptionHandler({ LinkedEntityNotFound.class })
  public final ResponseEntity<ApiError> handleLinkedEntityNotFoundException(LinkedEntityNotFound exception)
  {
    return getError(exception.getMessage(), BAD_REQUEST);
  }


  @ExceptionHandler({ InvalidPresentationTime.class })
  public final ResponseEntity<ApiError> handleInvalidPresentationTime(InvalidPresentationTime exception)
  {
    return getError(exception.getMessage(), BAD_REQUEST);
  }


  @ExceptionHandler({ TimeslotIsBooked.class })
  public final ResponseEntity<ApiError> handleTimeslotIsBooked(TimeslotIsBooked exception)
  {
    return getError(exception.getMessage(), BAD_REQUEST);
  }


  private ResponseEntity<ApiError> getError(String message, HttpStatus status)
  {
    return new ResponseEntity<>(new ApiError(message), status);
  }


  @Data
  @AllArgsConstructor
  private static class ApiError
  {
    private String message;
  }
}
