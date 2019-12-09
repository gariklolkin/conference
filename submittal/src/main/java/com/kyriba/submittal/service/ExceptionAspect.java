package com.kyriba.submittal.service;

import com.kyriba.submittal.service.exception.DuplicateObjectException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;


/**
 * @author M-ABL
 */
@Aspect
@Order(100)
@Configuration
public class ExceptionAspect
{
  @AfterThrowing(
      pointcut = "execution(* com.kyriba.submittal.service.SpeakerServiceImpl.register(..))",
      throwing = "ex")
  public void handleDuplicateEmailException(final DataIntegrityViolationException ex) throws Exception
  {
    throw new DuplicateObjectException("Email is already in use");
  }
}
