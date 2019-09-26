package com.kyriba.conference.management.domain;

import com.kyriba.conference.management.domain.dto.PresentationRequest;
import com.kyriba.conference.management.domain.dto.TopicDto;
import com.kyriba.conference.management.domain.exception.InvalidPresentationTimeException;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;

import static java.time.LocalTime.of;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class PresentationTest
{
  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @Test
  public void ifEndTimeBeforeStartTimeThrowException()
  {
    PresentationRequest request = new PresentationRequest();
    request.setStartTime(of(9, 0));
    request.setEndTime(of(8, 0));

    thrown.expect(InvalidPresentationTimeException.class);
    thrown.expectMessage("End time is before start time");

    new Presentation(request);
  }


  @Test
  public void testUpdate()
  {
    // given
    String title = "New Title";
    String author = "New Author";
    LocalTime startTime = of(8, 0);
    LocalTime endTime = of(9, 0);
    PresentationRequest request = new PresentationRequest(1, new TopicDto(title, author),
        startTime, endTime);

    Hall hall = new Hall();
    hall.setId(2L);
    hall.setName("Prime Hall");
    hall.setPlaces(50);

    Presentation presentation = createPresentation(hall);

    // when
    presentation.update(request);

    // then
    assertThat(presentation.getTopic()).isNotNull();
    assertThat(presentation.getTopic().getTitle()).isEqualTo(title);
    assertThat(presentation.getTopic().getAuthor()).isEqualTo(author);
    assertThat(presentation.getStartTime()).isEqualTo(startTime);
    assertThat(presentation.getEndTime()).isEqualTo(endTime);
  }


  @Test
  public void testUpdate_ifEndTimeBeforeStartTimeThrowException()
  {
    Presentation presentation = createPresentation(new Hall());

    PresentationRequest request = new PresentationRequest();
    request.setStartTime(of(9, 0));
    request.setEndTime(of(8, 0));

    thrown.expect(InvalidPresentationTimeException.class);
    thrown.expectMessage("End time is before start time");

    presentation.update(request);
  }


  @NotNull
  private Presentation createPresentation(Hall hall)
  {
    Presentation presentation = new Presentation();
    presentation.setId(100L);
    presentation.setHall(hall);
    presentation.setTopic(new Topic("Title", "Author"));
    presentation.setStartTime(of(18, 0));
    presentation.setEndTime(of(19, 0));
    return presentation;
  }
}
