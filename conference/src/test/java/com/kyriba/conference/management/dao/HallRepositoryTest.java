package com.kyriba.conference.management.dao;

import com.kyriba.conference.management.domain.Hall;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


@RunWith(SpringRunner.class)
public class HallRepositoryTest extends BaseRepositoryTest
{

  @Autowired
  protected HallRepository dao;


  @Test
  public void ifThereIsNoHallEmptyIsReturned()
  {
    assertThat(dao.findById(1134322L)).isEmpty();
  }


  @Test
  public void createHall()
  {
    final String name = "hall 01";
    final int places = 22;
    Hall hall = new Hall();
    hall.setName(name);
    hall.setPlaces(places);

    Hall createdHall = dao.save(hall);
    assertThat(createdHall).isNotNull();

    // check that Hall is stored
    Optional<Hall> storedResult = dao.findById(createdHall.getId());

    assertThat(storedResult).hasValueSatisfying(result -> {
      assertThat(result.getName()).isEqualTo(name);
      assertThat(result.getPlaces()).isEqualTo(places);
      assertThat(result.getId()).isPositive();
    });
  }


  @Test
  public void getAllHalls()
  {
    assertThat(dao.findAll()).extracting("name", "places")
        .containsOnly(
            tuple("test11", 10),
            tuple("test13", 13),
            tuple("test12", 12));
  }


  @Test
  public void findHallById()
  {
    assertThat(dao.findById(11L))
        .hasValueSatisfying(hall -> {
          assertThat(hall.getName()).isEqualTo("test11");
          assertThat(hall.getPlaces()).isEqualTo(10);
        });
  }


  @Test
  public void retunsEmptyIfNotFound()
  {
    assertThat(dao.findById(1100L)).isEmpty();
  }


  @Test
  public void removeHall()
  {
    final long id = 11L;
    // check Hall exists
    assertThat(dao.findById(id)).isNotEmpty();

    dao.deleteById(id);

    // check Hall removed
    assertThat(dao.findById(id)).isEmpty();
  }


  @Test
  public void removeNonexistentHall()
  {
    final long id = 9L;
    // check Hall does not exist
    assertThat(dao.findById(id)).isEmpty();

    thrown.expect(EmptyResultDataAccessException.class);

    dao.deleteById(id);
  }

}