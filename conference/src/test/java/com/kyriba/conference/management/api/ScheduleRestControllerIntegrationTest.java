package com.kyriba.conference.management.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriba.conference.management.domain.dto.PresentationRequest;
import com.kyriba.conference.management.domain.dto.PresentationResponse;
import com.kyriba.conference.management.domain.dto.TopicDto;
import com.kyriba.conference.management.domain.exception.InvalidPresentationTimeException;
import com.kyriba.conference.management.domain.exception.LinkedEntityNotFoundException;
import com.kyriba.conference.management.domain.exception.PresentationTimeIntersectionException;
import com.kyriba.conference.management.service.ScheduleService;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.Optional;

import static java.time.LocalTime.of;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ScheduleController.class)
@ActiveProfiles("test")
public class ScheduleRestControllerIntegrationTest
{
  private static final DateTimeFormatter HHmm = DateTimeFormatter.ofPattern("HH:mm");

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ScheduleService scheduleService;


  @Test
  public void addPresentationEndpointTest() throws Exception
  {
    final long hallId = 13;
    final String topicTitle = "Concurrency";
    final String topicAuthor = "Brian Goetz";
    final LocalTime startTime = of(9, 0);
    final LocalTime endTime = of(10, 0);
    TopicDto topic = new TopicDto(topicTitle, topicAuthor);
    PresentationRequest presentationRequest = new PresentationRequest(hallId, topic, startTime, endTime);
    doReturn(7L).when(scheduleService).addPresentation(refEq(presentationRequest));

    mockMvc.perform(post("/api/v1/schedule/presentations")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"hall\": " + hallId + ",\n" +
            "  \"topic\": {\n" +
            "    \"title\": \"" + topicTitle + "\",\n" +
            "    \"author\" : \"" + topicAuthor + "\"\n" +
            "  },\n" +
            "  \"startTime\": \"" + startTime.format(HHmm) + "\",\n" +
            "  \"endTime\" : \"" + endTime.format(HHmm) + "\"\n" +
            "}"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json("{\"id\":7}"));
  }


  @Test
  public void addPresentation_ifHallNotExistsResponseBadRequest() throws Exception
  {
    final String error = "Hall not found.";
    doThrow(new LinkedEntityNotFoundException(error)).when(scheduleService)
        .addPresentation(any(PresentationRequest.class));

    mockMvc.perform(post("/api/v1/schedule/presentations")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"hall\": 13,\n" +
            "  \"topic\": {\n" +
            "    \"title\": \"Concurrency\",\n" +
            "    \"author\" : \"Brian Goetz\"\n" +
            "  },\n" +
            "  \"startTime\": \"" + of(9, 0).format(HHmm) + "\",\n" +
            "  \"endTime\" : \"" + of(10, 0).format(HHmm) + "\"\n" +
            "}"))
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(error));
  }


  @Test
  public void addPresentation_ifTopicNotCorrectResponseBadRequest() throws Exception
  {
    mockMvc.perform(post("/api/v1/schedule/presentations")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"hall\": 11,\n" +
            "  \"topic\": {\n" +
            "    \"title\": \" \",\n" +
            "    \"author\" : \" \"\n" +
            "  },\n" +
            "  \"startTime\": \" \",\n" +
            "  \"endTime\" : \"" + of(10, 0).format(HHmm) + "\"\n" +
            "}"))
        .andExpect(status().isBadRequest());
  }


  @Test
  public void addPresentation_ifTimeNotCorrectResponseBadRequest() throws Exception
  {
    final String error = "End time is before start time";
    doThrow(new InvalidPresentationTimeException(error)).when(scheduleService)
        .addPresentation(any(PresentationRequest.class));

    mockMvc.perform(post("/api/v1/schedule/presentations")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"hall\": 11,\n" +
            "  \"topic\": {\n" +
            "    \"title\": \"Concurrency\",\n" +
            "    \"author\" : \"Brian Goetz\"\n" +
            "  },\n" +
            "  \"startTime\": \"" + of(10, 0).format(HHmm) + "\",\n" +
            "  \"endTime\" : \"" + of(9, 0).format(HHmm) + "\"\n" +
            "}"))
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(error));
  }


  @Test
  public void addPresentation_ifTimeIntersectionResponseBadRequest() throws Exception
  {
    final LocalTime startTime = of(9, 0);
    final LocalTime endTime = of(10, 0);
    doThrow(new PresentationTimeIntersectionException("Time intersection")).when(scheduleService)
        .addPresentation(any(PresentationRequest.class));

    mockMvc.perform(post("/api/v1/schedule/presentations")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content("{\n" +
            "  \"hall\": 11,\n" +
            "  \"topic\": {\n" +
            "    \"title\": \"Concurrency\",\n" +
            "    \"author\" : \"Brian Goetz\"\n" +
            "  },\n" +
            "  \"startTime\": \"" + startTime.format(HHmm) + "\",\n" +
            "  \"endTime\" : \"" + endTime.format(HHmm) + "\"\n" +
            "}"))
        .andExpect(status().isBadRequest())
        .andExpect(status().reason("Time intersection"));
  }


  @Test
  public void getPresentationEndpointTest() throws Exception
  {
    final long hallId = 13L;
    final String topicTitle = "Concurrency";
    final String topicAuthor = "Brian Goetz";
    TopicDto topic = new TopicDto(topicTitle, topicAuthor);
    final LocalTime startTime = of(9, 0);
    final LocalTime endTime = of(10, 0);
    PresentationResponse presentationResponse = new PresentationResponse(hallId, topic, startTime, endTime);
    doReturn(Optional.of(presentationResponse)).when(scheduleService).getPresentation(anyLong());

    mockMvc.perform(get("/api/v1/schedule/presentations/13"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json("{\n" +
            "  \"hall\": " + hallId + ",\n" +
            "  \"topic\": {\n" +
            "    \"title\": \"" + topicTitle + "\",\n" +
            "    \"author\" : \"" + topicAuthor + "\"\n" +
            "  },\n" +
            "  \"startTime\": \"" + startTime.format(HHmm) + "\",\n" +
            "  \"endTime\" : \"" + endTime.format(HHmm) + "\"\n" +
            "}"));
  }


  @Test
  public void getPresentation_ifPresentationNotExistResponseNotFound() throws Exception
  {
    doReturn(Optional.empty()).when(scheduleService).getPresentation(anyLong());

    mockMvc.perform(get("/api/v1/schedule/presentations/13"))
        .andExpect(status().isNotFound());
  }


  @Test
  public void getPresentation_ifIncorrectIdResponseBadRequest() throws Exception
  {
    doThrow(mock(ConstraintViolationException.class)).when(scheduleService).getPresentation(anyLong());

    mockMvc.perform(get("/api/v1/schedule/presentations/-13"))
        .andExpect(status().isBadRequest());
  }


  @Test
  public void removePresentationEndpointTest() throws Exception
  {
    mockMvc.perform(delete("/api/v1/schedule/presentations/1000"))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$").doesNotExist());

    verify(scheduleService).deletePresentation(eq(1000L));
  }


  @Test
  public void removePresentation_ifIncorrectIdResponseBadRequest() throws Exception
  {

    doThrow(mock(ConstraintViolationException.class))
        .when(scheduleService).deletePresentation(anyLong());

    mockMvc.perform(delete("/api/v1/schedule/presentations/-1000"))
        .andExpect(status().isBadRequest());

    verify(scheduleService).deletePresentation(eq(-1000L));
  }


  @Test
  public void getScheduleEndpointTest() throws Exception
  {
    TopicDto topic = new TopicDto("Concurrency", "Brian Goetz");
    final LocalTime startTime = of(9, 0);
    final LocalTime endTime = of(10, 0);
    PresentationResponse presentation1 = new PresentationResponse(11, topic, startTime, endTime);
    PresentationResponse presentation2 = new PresentationResponse(12, topic, startTime.plusHours(1), endTime.plusHours(1));
    doReturn(asList(presentation1, presentation2)).when(scheduleService).getSchedule();

    MvcResult result = mockMvc.perform(get("/api/v1/schedule"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andReturn();

    String contentAsString = result.getResponse().getContentAsString();
    ScheduleController.ScheduleResponse schedule = objectMapper.readValue(contentAsString, ScheduleController.ScheduleResponse.class);

    assertThat(schedule).isNotNull();
    assertThat(schedule.getPresentations()).isNotEmpty()
        .containsExactlyInAnyOrder(presentation1, presentation2);
  }


  @Test
  public void getSchedule_empty() throws Exception
  {
    doReturn(emptyList()).when(scheduleService).getSchedule();

    MvcResult result = mockMvc.perform(get("/api/v1/schedule"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andReturn();

    String contentAsString = result.getResponse().getContentAsString();
    ScheduleController.ScheduleResponse schedule = objectMapper.readValue(contentAsString, ScheduleController.ScheduleResponse.class);

    assertThat(schedule).isNotNull();
    assertThat(schedule.getPresentations()).isEmpty();
  }
}
