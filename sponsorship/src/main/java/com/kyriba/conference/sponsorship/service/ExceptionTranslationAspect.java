package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.service.exception.DuplicatedObjectException;
import com.kyriba.conference.sponsorship.service.exception.ObjectNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * @author Aliaksandr Samal
 */
@Aspect
@Order(100)
@Configuration
public class ExceptionTranslationAspect
{
  @Pointcut("execution(* com.kyriba.conference.sponsorship.service.SponsorService.createSponsor(..))")
  public void createSponsor() { }


  @Pointcut("execution(* com.kyriba.conference.sponsorship.service.PlanService.createPlan(..))")
  public void createPlan() { }


  @AfterThrowing(pointcut = "createPlan() || createSponsor()", throwing = "e")
  public void handleDuplicateKeyConstraint(DataIntegrityViolationException e)
  {
    throw new DuplicatedObjectException(e);
  }


  @Pointcut("execution(* com.kyriba.conference.sponsorship.service.PlanService.deletePlan(..))")
  public void deletePlan() { }


  @Around("deletePlan() ")
  public void handleNonexistentEntityDeletion(ProceedingJoinPoint pjp) throws Throwable
  {
    try {
      pjp.proceed();
    }
    catch (EmptyResultDataAccessException e) {
      throw new ObjectNotFoundException(e);
    }
  }
}