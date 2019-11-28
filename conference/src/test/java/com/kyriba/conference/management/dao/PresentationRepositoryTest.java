package com.kyriba.conference.management.dao;


import com.kyriba.conference.management.domain.Hall;
import com.kyriba.conference.management.domain.Presentation;
import com.kyriba.conference.management.domain.Topic;
import org.hamcrest.Matchers;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.time.LocalTime.of;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class PresentationRepositoryTest extends BaseRepositoryTest
{
  @Autowired
  protected PresentationRepository dao;


  @Test
  public void ifThereIsNoPresentationEmptyIsReturned()
  {
    assertThat(dao.findById(1134322L)).isEmpty();
  }


  @Test
  public void createPresentation()
  {
    Hall hall = new Hall();
    long hallId = 11L;
    hall.setId(hallId);
    hall.setName("hall 11");
    hall.setPlaces(22);
    final LocalTime startTime = of(9, 0);
    final LocalTime endTime = of(10, 0);

    Presentation presentation = new Presentation();
    presentation.setHall(hall);
    String title = "Topic1";
    String author = "author1";
    presentation.setTopic(new Topic(title, author));
    presentation.setStartTime(startTime);
    presentation.setEndTime(endTime);

    Presentation createdPresentation = dao.save(presentation);
    assertThat(createdPresentation).isNotNull();

    // check that Presentation is stored
    Optional<Presentation> storedResult = dao.findById(createdPresentation.getId());

    assertThat(storedResult).isNotEmpty().hasValueSatisfying(result -> {
      assertThat(result.getStartTime()).isEqualTo(startTime);
      assertThat(result.getEndTime()).isEqualTo(endTime);
      assertThat(result.getHall()).isNotNull()
          .hasFieldOrPropertyWithValue("id", hallId);
      assertThat(result.getTopic()).isNotNull()
          .satisfies(topic -> {
            assertThat(topic.getTitle()).isEqualTo(title);
            assertThat(topic.getAuthor()).isEqualTo(author);
          });
      assertThat(result.getId()).isPositive();
    });
  }


  @Test
  public void createPresentationWithNonexistentHallThrowsException()
  {
    Hall hall = new Hall();
    hall.setId(11000L);
    hall.setName("hall 11");
    hall.setPlaces(22);

    Presentation presentation = new Presentation();
    presentation.setHall(hall);
    presentation.setTopic(new Topic("Topic1", "author1"));
    presentation.setStartTime(of(9, 0));
    presentation.setEndTime(of(10, 0));

    thrown.expect(DataIntegrityViolationException.class);
    thrown.expectCause(Matchers.isA(ConstraintViolationException.class));

    dao.save(presentation);
  }


  @Test
  public void readSchedule()
  {
    List<Presentation> schedule = dao.findAll();

    assertThat(schedule).isNotNull().hasSize(3)
        .extracting("topic").isNotNull()
        .extracting("title")
        .containsOnly("Spring Data REST", "Microservices in practice", "All about Spring workshop");
  }


  @Test
  public void findPresentationById()
  {
    assertThat(dao.findById(1001L)).isNotEmpty().hasValueSatisfying(result -> {
      assertThat(result.getStartTime()).isEqualTo(of(10, 0));
      assertThat(result.getEndTime()).isEqualTo(of(11, 15));
      assertThat(result.getHall()).isNotNull()
          .hasFieldOrPropertyWithValue("id", 11L);
      assertThat(result.getTopic()).isNotNull()
          .satisfies(topic -> {
            assertThat(topic.getTitle()).isEqualTo("Spring Data REST");
            assertThat(topic.getAuthor()).isEqualTo("Andy Wilkinson");
          });
      assertThat(result.getId()).isEqualTo(1001L);
    });
  }


  @Test
  public void findPresentationByIdRetunsEmptyIfNotFound()
  {
    assertThat(dao.findById(9L)).isEmpty();
  }


  @Test
  public void removePresentation()
  {
    final long id = 1001L;
    // check Presentation exists
    assertThat(dao.findById(id)).isNotEmpty();

    dao.deleteById(id);

    // check Presentation removed
    assertThat(dao.findById(id)).isEmpty();
  }


  @Test
  public void removeNonexistentPresentation()
  {
    final long id = 9L;
    // check Presentation does not exist
    assertThat(dao.findById(id)).isEmpty();

    thrown.expect(EmptyResultDataAccessException.class);

    dao.deleteById(id);
  }

}
