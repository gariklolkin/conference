package com.kyriba.conference.management.service;


import com.kyriba.conference.management.domain.exception.EntityIsUsedException;
import com.kyriba.conference.management.domain.exception.SameEntityExistsException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;


@Aspect
@Order(100)
@Configuration
public class ExceptionTranslationAspect
{
  @Pointcut("execution(* com.kyriba.conference.management.service.HallServiceImpl.createHall(..))")
  public void createHall()
  {
  }


  @Pointcut("execution(* com.kyriba.conference.management.service.ScheduleServiceImpl.addPresentation(..))")
  public void createPresentation()
  {
  }


  @AfterThrowing(
      pointcut = "createHall() || createPresentation()",
      throwing = "e")
  public void handleDuplicateKeyConstraint(DataIntegrityViolationException e)
  {
    throw new SameEntityExistsException(e);
  }


  @Pointcut("execution(* com.kyriba.conference.management.service.HallServiceImpl.removeHall(..))")
  public void removeHall()
  {
  }


  @Pointcut("execution(* com.kyriba.conference.management.service.ScheduleServiceImpl.deletePresentation(..))")
  public void removePresentation()
  {
  }


  @Around("removeHall() || removePresentation()")
  public void handleNonexistentEntityDeletion(ProceedingJoinPoint pjp) throws Throwable
  {
    try {
      pjp.proceed();
    }
    catch (EmptyResultDataAccessException e) {
      // do nothing
    }
    catch (DataIntegrityViolationException e) {
      throw new EntityIsUsedException(e);
    }
  }
}
