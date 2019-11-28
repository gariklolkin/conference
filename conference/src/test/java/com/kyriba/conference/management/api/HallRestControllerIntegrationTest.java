package com.kyriba.conference.management.api;


import com.kyriba.conference.management.domain.dto.HallRequest;
import com.kyriba.conference.management.domain.dto.HallResponse;
import com.kyriba.conference.management.domain.exception.EntityIsUsedException;
import com.kyriba.conference.management.domain.exception.EntityNotFoundException;
import com.kyriba.conference.management.domain.exception.SameEntityExistsException;
import com.kyriba.conference.management.service.HallService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(HallController.class)
@ActiveProfiles("test")
public class HallRestControllerIntegrationTest
{
  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private HallService hallService;

  private HallRequest hallRequest = new HallRequest("My Hall #1", 10);


  @Test
  public void createHallWithUniqueId() throws Exception
  {
    doReturn(7L).when(hallService).createHall(refEq(hallRequest));

    mockMvc.perform(post("/api/v1/halls")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"name\": \"" + hallRequest.getName() + "\",\n" +
            "  \"places\": \"" + hallRequest.getPlaces() + "\"\n" +
            "}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json("{\"id\":7}"));
  }


  @Test
  public void ifNameIsBlankResponseBadRequest() throws Exception
  {
    mockMvc.perform(post("/api/v1/halls")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"name\": \" \\t\",\n" +
            "  \"places\": \"10\"\n" +
            "}"))
        .andExpect(status().isBadRequest());
  }


  @Test
  public void ifNameIsAlreadyUsedResponseConflict() throws Exception
  {
    doThrow(new SameEntityExistsException(new Exception("Unique constraint violation")))
        .when(hallService).createHall(refEq(hallRequest));

    mockMvc.perform(post("/api/v1/halls")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"name\": \"" + hallRequest.getName() + "\",\n" +
            "  \"places\": \"" + hallRequest.getPlaces() + "\"\n" +
            "}"))
        .andExpect(status().isConflict())
        .andExpect(status().reason("Hall with the same name already exists."));
  }


  @Test
  public void ifPlacesNotEnoughResponseBadRequest() throws Exception
  {
    mockMvc.perform(post("/api/v1/halls")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"name\": \"Hall #2\",\n" +
            "  \"places\": \"9\"\n" +
            "}"))
        .andExpect(status().isBadRequest());
  }


  @Test
  public void findAllHalls() throws Exception
  {
    List<HallResponse> allHalls = asList(new HallResponse("H1", 10),
        new HallResponse("H2", 20));
    doReturn(allHalls).when(hallService).findAllHalls();

    mockMvc.perform(get("/api/v1/halls"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json("[{\n" +
            "  \"name\": \"H1\",\n" +
            "  \"places\": 10\n" +
            "},\n" +
            "{\n" +
            "  \"name\": \"H2\",\n" +
            "  \"places\": 20\n" +
            "}]"
        ));
  }


  @Test
  public void ifNoHallsExistReturnEmpty() throws Exception
  {
    doReturn(emptyList()).when(hallService).findAllHalls();

    mockMvc.perform(get("/api/v1/halls"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json("[]"));
  }


  @Test
  public void findHallById() throws Exception
  {
    HallResponse hall = new HallResponse("H1", 13);
    doReturn(hall).when(hallService).findHall(anyLong());

    mockMvc.perform(get("/api/v1/halls/11"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json("{\n" +
            "  \"name\": \"H1\",\n" +
            "  \"places\": 13\n" +
            "}"));
  }


  @Test
  public void ifHallNotExistResponseNotFound() throws Exception
  {
    doThrow(new EntityNotFoundException("NotFound"))
        .when(hallService).findHall(anyLong());

    mockMvc.perform(get("/api/v1/halls/11"))
        .andExpect(status().isNotFound())
        .andExpect(status().reason("Hall not found."));
  }


  @Test
  public void ifIncorrectHallIdResponseBadRequest() throws Exception
  {
    mockMvc.perform(get("/api/v1/halls/-2A2"))
        .andExpect(status().isBadRequest());
  }


  @Test
  public void updateHallById() throws Exception
  {
    mockMvc.perform(put("/api/v1/halls/22")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"name\": \"" + hallRequest.getName() + "\",\n" +
            "  \"places\": \"" + hallRequest.getPlaces() + "\"\n" +
            "}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").doesNotExist());

    verify(hallService).updateHall(eq(22L), refEq(hallRequest));
  }


  @Test
  public void ifIncorrectHallIdInUpdateResponseBadRequest() throws Exception
  {
    doThrow(mock(ConstraintViolationException.class))
        .when(hallService).updateHall(anyLong(), any(HallRequest.class));

    mockMvc.perform(put("/api/v1/halls/-22")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"name\": \"" + hallRequest.getName() + "\",\n" +
            "  \"places\": \"" + hallRequest.getPlaces() + "\"\n" +
            "}"))
        .andExpect(status().isBadRequest());
  }


  @Test
  public void ifChangedNameIsAlreadyUsedResponseConflict() throws Exception
  {
    doThrow(new SameEntityExistsException(new Exception("Unique constraint violation")))
        .when(hallService).updateHall(eq(22L), refEq(hallRequest));

    mockMvc.perform(put("/api/v1/halls/22")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"name\": \"" + hallRequest.getName() + "\",\n" +
            "  \"places\": \"" + hallRequest.getPlaces() + "\"\n" +
            "}"))
        .andExpect(status().isConflict())
        .andExpect(status().reason("Hall with the same name already exists."));
  }


  @Test
  public void deleteHallById() throws Exception
  {
    mockMvc.perform(delete("/api/v1/halls/22"))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$").doesNotExist());

    verify(hallService).removeHall(eq(22L));
  }


  @Test
  public void ifIncorrectHallIdInDeletionResponseBadRequest() throws Exception
  {
    doThrow(mock(ConstraintViolationException.class))
        .when(hallService).removeHall(anyLong());

    mockMvc.perform(delete("/api/v1/halls/-22"))
        .andExpect(status().isBadRequest());

    verify(hallService).removeHall(eq(-22L));
  }


  @Test
  public void ifUsedResponseConflict() throws Exception
  {
    doThrow(mock(EntityIsUsedException.class)).when(hallService).removeHall(anyLong());

    mockMvc.perform(delete("/api/v1/halls/1"))
        .andExpect(status().isConflict())
        .andExpect(status().reason("Cannot remove Hall because it is used."));

    verify(hallService).removeHall(eq(1L));
  }

}
