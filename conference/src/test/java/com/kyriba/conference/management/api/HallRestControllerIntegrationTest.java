package com.kyriba.conference.management.api;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MvcResult;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
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

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private HallService hallService;

  private HallRequest hallRequest = new HallRequest("My Hall #1", 10);


  @Test
  public void createHallEndpointTest() throws Exception
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
  public void createHall_ifNameIsBlankResponseBadRequest() throws Exception
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
  public void createHall_ifNameIsAlreadyUsedResponseConflict() throws Exception
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
  public void createHall_ifPlacesNotEnoughResponseBadRequest() throws Exception
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
  public void findAllHallsEndpointTest() throws Exception
  {
    List<HallResponse> allHalls = asList(new HallResponse("H1", 10),
        new HallResponse("H2", 20));
    doReturn(allHalls).when(hallService).findAllHalls();

    MvcResult result = mockMvc.perform(get("/api/v1/halls"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andReturn();

    String contentAsString = result.getResponse().getContentAsString();
    HallResponse[] foundHalls = objectMapper.readValue(contentAsString, HallResponse[].class);

    assertThat(foundHalls).isNotNull();
    assertThat(foundHalls).extracting("name", "places")
        .containsOnly(
            tuple("H1", 10),
            tuple("H2", 20));
  }


  @Test
  public void findAllHalls_ifNoHallsExistReturnEmpty() throws Exception
  {
    doReturn(emptyList()).when(hallService).findAllHalls();

    MvcResult result = mockMvc.perform(get("/api/v1/halls"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andReturn();

    String contentAsString = result.getResponse().getContentAsString();
    HallResponse[] foundHalls = objectMapper.readValue(contentAsString, HallResponse[].class);

    assertThat(foundHalls).isEmpty();
  }


  @Test
  public void findHallEndpointTest() throws Exception
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
  public void findHall_ifHallNotExistResponseNotFound() throws Exception
  {
    doThrow(new EntityNotFoundException("NotFound"))
        .when(hallService).findHall(anyLong());

    mockMvc.perform(get("/api/v1/halls/11"))
        .andExpect(status().isNotFound())
        .andExpect(status().reason("Hall not found."));
  }


  @Test
  public void findHall_ifIncorrectHallIdResponseBadRequest() throws Exception
  {
    mockMvc.perform(get("/api/v1/halls/-2A2"))
        .andExpect(status().isBadRequest());
  }


  @Test
  public void updateHallEndpointTest() throws Exception
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
  public void updateHall_ifIncorrectHallIdResponseBadRequest() throws Exception
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
  public void updateHall_ifChangedNameIsAlreadyUsedResponseConflict() throws Exception
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
  public void deleteHallEndpointTest() throws Exception
  {
    mockMvc.perform(delete("/api/v1/halls/22"))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$").doesNotExist());

    verify(hallService).removeHall(eq(22L));
  }


  @Test
  public void deleteHall_ifIncorrectHallIdResponseBadRequest() throws Exception
  {
    doThrow(mock(ConstraintViolationException.class))
        .when(hallService).removeHall(anyLong());

    mockMvc.perform(delete("/api/v1/halls/-22"))
        .andExpect(status().isBadRequest());

    verify(hallService).removeHall(eq(-22L));
  }


  @Test
  public void deleteHall_ifUsedResponseConflict() throws Exception
  {
    doThrow(mock(EntityIsUsedException.class)).when(hallService).removeHall(anyLong());

    mockMvc.perform(delete("/api/v1/halls/1"))
        .andExpect(status().isConflict())
        .andExpect(status().reason("Cannot remove Hall because it is used."));

    verify(hallService).removeHall(eq(1L));
  }

}
