package com.kyriba.conference.management.service;


import com.kyriba.conference.management.dao.HallRepository;
import com.kyriba.conference.management.dao.PresentationRepository;
import com.kyriba.conference.management.domain.Hall;
import com.kyriba.conference.management.domain.Presentation;
import com.kyriba.conference.management.domain.Topic;
import com.kyriba.conference.management.domain.dto.PresentationRequest;
import com.kyriba.conference.management.domain.dto.PresentationResponse;
import com.kyriba.conference.management.domain.dto.TopicDto;
import com.kyriba.conference.management.domain.exception.EntityNotFoundException;
import com.kyriba.conference.management.domain.exception.LinkedEntityNotFoundException;
import com.kyriba.conference.management.domain.exception.PresentationTimeIntersectionException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.time.LocalTime.of;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceImplTest
{
  private static final String TITLE_1 = "Topic1";
  private static final String AUTHOR_1 = "author1";
  private static final String TITLE_2 = "Topic2";
  private static final String AUTHOR_2 = "author2";
  private static final LocalTime START_TIME = of(9, 0);
  private static final LocalTime END_TIME = of(10, 0);

  @Mock
  private HallRepository hallRepository;
  @Mock
  private PresentationRepository presentationRepository;

  @InjectMocks
  private ScheduleServiceImpl scheduleService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @Test
  public void getSchedule()
  {
    Hall hall = createHall();
    Presentation pr1 = createPresentation(TITLE_1, AUTHOR_1, START_TIME, END_TIME, hall);
    Presentation pr2 = createPresentation(TITLE_2, AUTHOR_2, START_TIME.plusHours(1), END_TIME.plusHours(1), hall);

    PresentationResponse presentation1 = new PresentationResponse(1, new TopicDto(TITLE_1, AUTHOR_1), START_TIME,
        END_TIME);
    PresentationResponse presentation2 = new PresentationResponse(1, new TopicDto(TITLE_2, AUTHOR_2),
        START_TIME.plusHours(1), END_TIME.plusHours(1));

    doReturn(asList(pr1, pr2)).when(presentationRepository).findAll();

    List<PresentationResponse> result = scheduleService.getSchedule();

    assertThat(result).isNotEmpty()
        .containsExactlyInAnyOrder(presentation1, presentation2);
  }


  @Test
  public void getPresentation()
  {
    long id = 2L;

    Hall hall = createHall();
    Presentation pr1 = createPresentation(TITLE_1, AUTHOR_1, START_TIME, END_TIME, hall);

    doReturn(Optional.of(pr1)).when(presentationRepository).findById(eq((id)));

    Optional<PresentationResponse> result = scheduleService.getPresentation(id);

    assertThat(result).isNotEmpty()
        .usingFieldByFieldValueComparator()
        .contains(new PresentationResponse(hall.getId(), new TopicDto(TITLE_1, AUTHOR_1), START_TIME, END_TIME));
  }


  @Test
  public void ifNotFoundReturnEmpty()
  {
    long id = 2L;
    doReturn(empty()).when(presentationRepository).findById(eq((id)));

    assertThat(scheduleService.getPresentation(id)).isEmpty();
  }


  @Test
  public void addPresentation()
  {
    Hall hall = new Hall();
    PresentationRequest request = createPresentationRequest();
    Presentation presentationToCreate = new Presentation(request);
    presentationToCreate.setHall(hall);
    Presentation createdPresentation = new Presentation(request);
    long createdId = 4L;
    createdPresentation.setId(createdId);

    doReturn(Optional.of(hall)).when(hallRepository).findById(anyLong());
    doReturn(createdPresentation).when(presentationRepository).save(any(Presentation.class));

    final long id = scheduleService.addPresentation(request);

    assertThat(id).isEqualTo(createdId);
    verify(presentationRepository).save(refEq(presentationToCreate, "topic"));
  }


  @Test
  public void ifHallNotFoundThrowException()
  {
    PresentationRequest request = createPresentationRequest();
    doReturn(empty()).when(hallRepository).findById(anyLong());

    thrown.expect(LinkedEntityNotFoundException.class);
    thrown.expectMessage("Hall not found.");

    scheduleService.addPresentation(request);
  }


  @Test
  public void ifTimeIsNotAvailableThrowException()
  {
    PresentationRequest request = createPresentationRequest();
    Presentation intersectedPresentation = new Presentation(request);
    intersectedPresentation.setId(2L);

    doReturn(Optional.of(new Hall())).when(hallRepository).findById(anyLong());
    doReturn(Stream.of(intersectedPresentation)).when(presentationRepository)
        .findByHallAndStartTimeOrEndTimeBetween(any(Hall.class), any(LocalTime.class), any(LocalTime.class));

    thrown.expect(PresentationTimeIntersectionException.class);
    thrown.expectMessage("Presentation time intersection: wanted 09:00 - 10:00, already reserved 09:00 - 10:00.");

    scheduleService.addPresentation(request);
  }


  @Test
  public void updatePresentation()
  {
    Hall hall = new Hall();

    PresentationRequest request = createPresentationRequest();
    Presentation presentationToUpdate = new Presentation(request);
    presentationToUpdate.setHall(hall);
    long id = 4L;

    doReturn(Optional.of(hall)).when(hallRepository).findById(anyLong());
    doReturn(Optional.of(presentationToUpdate)).when(presentationRepository).findById(eq(id));

    scheduleService.updatePresentation(id, request);

    verify(presentationRepository).save(refEq(presentationToUpdate));
  }


  @Test
  public void ifNotFoundThrowException()
  {
    PresentationRequest request = createPresentationRequest();
    long id = 4L;

    doReturn(empty()).when(presentationRepository).findById(eq(id));

    thrown.expect(EntityNotFoundException.class);
    thrown.expectMessage("Presentation not found.");

    scheduleService.updatePresentation(id, request);
  }


  @Test
  public void ifHallNotFoundInUpdateThrowException()
  {
    PresentationRequest request = createPresentationRequest();
    Presentation presentationToUpdate = new Presentation(request);
    presentationToUpdate.setHall(new Hall());
    long id = 4L;

    doReturn(empty()).when(hallRepository).findById(anyLong());
    doReturn(Optional.of(presentationToUpdate)).when(presentationRepository).findById(eq(id));

    thrown.expect(LinkedEntityNotFoundException.class);
    thrown.expectMessage("Hall not found.");

    scheduleService.updatePresentation(id, request);
  }


  @Test
  public void ifTimeIsNotAvailableInUpdateThrowException()
  {
    Hall hall = new Hall();
    long id = 4L;

    PresentationRequest request = createPresentationRequest();
    Presentation intersectedPresentation = new Presentation(request);
    intersectedPresentation.setId(2L);

    Presentation presentationToUpdate = new Presentation(request);
    presentationToUpdate.setId(id);
    presentationToUpdate.setHall(hall);

    doReturn(Optional.of(hall)).when(hallRepository).findById(anyLong());
    doReturn(Optional.of(presentationToUpdate)).when(presentationRepository).findById(eq(id));
    doReturn(Stream.of(intersectedPresentation)).when(presentationRepository)
        .findByHallAndStartTimeOrEndTimeBetween(any(Hall.class), any(LocalTime.class), any(LocalTime.class));

    thrown.expect(PresentationTimeIntersectionException.class);
    thrown.expectMessage("Presentation time intersection: wanted 09:00 - 10:00, already reserved 09:00 - 10:00.");

    scheduleService.updatePresentation(id, request);
  }


  @Test
  public void deletePresentation()
  {
    long id = 4L;
    scheduleService.deletePresentation(id);

    verify(presentationRepository).deleteById(eq(id));
  }


  private PresentationRequest createPresentationRequest()
  {
    return new PresentationRequest(1, new TopicDto(TITLE_1, AUTHOR_1), START_TIME, END_TIME);
  }


  private Hall createHall()
  {
    Hall hall = new Hall();
    hall.setId(1L);
    hall.setName("H1");
    hall.setPlaces(11);
    return hall;
  }


  private Presentation createPresentation(String title1, String author1, LocalTime startTime, LocalTime endTime,
                                          Hall hall)
  {
    Presentation pr1 = new Presentation();
    pr1.setHall(hall);
    pr1.setTopic(new Topic(title1, author1));
    pr1.setStartTime(startTime);
    pr1.setEndTime(endTime);
    return pr1;
  }
}
