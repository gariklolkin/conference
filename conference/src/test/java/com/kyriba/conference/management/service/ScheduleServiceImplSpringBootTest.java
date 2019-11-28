package com.kyriba.conference.management.service;


import com.kyriba.conference.management.dao.HallRepository;
import com.kyriba.conference.management.dao.PresentationRepository;
import com.kyriba.conference.management.domain.dto.PresentationRequest;
import com.kyriba.conference.management.domain.dto.TopicDto;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static java.time.LocalTime.of;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ScheduleServiceImpl.class, ValidationAutoConfiguration.class })
public class ScheduleServiceImplSpringBootTest
{
  @MockBean
  private PresentationRepository presentationRepository;
  @MockBean
  private HallRepository hallRepository;

  @Autowired
  private ScheduleService scheduleService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @Test
  public void ifIdNotPositiveThrowException()
  {
    thrown.expect(ConstraintViolationException.class);
    thrown.expectMessage("id: must be greater than 0");

    scheduleService.getPresentation(-22L);
  }


  @Test
  public void ifNegativeIdAndEmptyAuthorThrowException()
  {
    final long hallId = -13;
    TopicDto topic = new TopicDto("Concurrency", "");
    PresentationRequest presentationRequest = new PresentationRequest(hallId, topic, of(9, 0), of(10, 0));

    thrown.expect(ConstraintViolationException.class);
    thrown.expectMessage("author: must not be blank");
    thrown.expectMessage("hall: must be greater than 0");

    scheduleService.addPresentation(presentationRequest);
  }


  @Test
  public void ifNegativeIdThrowException()
  {
    thrown.expect(ConstraintViolationException.class);
    thrown.expectMessage("id: must be greater than 0");

    scheduleService.deletePresentation(-1L);
  }
}
