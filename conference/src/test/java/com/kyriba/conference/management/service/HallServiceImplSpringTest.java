package com.kyriba.conference.management.service;

import com.kyriba.conference.management.dao.HallRepository;
import com.kyriba.conference.management.domain.dto.HallRequest;
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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { HallServiceImpl.class, ValidationAutoConfiguration.class })
public class HallServiceImplSpringTest
{
  @MockBean
  private HallRepository dao;

  @Autowired
  private HallService hallService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @Test
  public void ifHallIdNotPositiveThrowException()
  {
    thrown.expect(ConstraintViolationException.class);
    thrown.expectMessage("id: must be greater than 0");

    hallService.findHall(-22L);
  }


  @Test
  public void ifBlankNameAndNotEnoughPlacesThrowException()
  {
    HallRequest hallRequest = new HallRequest();
    hallRequest.setName("");
    hallRequest.setPlaces(1);

    thrown.expect(ConstraintViolationException.class);
    thrown.expectMessage("name: must not be blank");
    thrown.expectMessage("places: must be greater than or equal to 10");

    hallService.createHall(hallRequest);
  }


  @Test
  public void ifBlankNameAndNotEnoughPlacesAndNegativeIdThrowException()
  {
    HallRequest hallRequest = new HallRequest();
    hallRequest.setName("");
    hallRequest.setPlaces(1);

    thrown.expect(ConstraintViolationException.class);
    thrown.expectMessage("name: must not be blank");
    thrown.expectMessage("places: must be greater than or equal to 10");
    thrown.expectMessage("id: must be greater than 0");

    hallService.updateHall(-1L, hallRequest);
  }


  @Test
  public void ifNegativeIdInRemoveThrowException()
  {
    thrown.expect(ConstraintViolationException.class);
    thrown.expectMessage("id: must be greater than 0");

    hallService.removeHall(-1L);
  }
}
