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

import javax.validation.ConstraintViolationException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.kyriba.conference.management.api.TestHelper.getPresentationJson;
import static java.time.LocalTime.of;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
  public void addPresentationWithUniqueId() throws Exception
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
        .content(getPresentationJson(hallId, topicTitle, topicAuthor, startTime, endTime)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json("{\"id\":7}"));
  }


  @Test
  public void ifHallNotExistsResponseBadRequest() throws Exception
  {
    final String error = "Hall not found.";
    doThrow(new LinkedEntityNotFoundException(error)).when(scheduleService)
        .addPresentation(any(PresentationRequest.class));

    mockMvc.perform(post("/api/v1/schedule/presentations")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content(getPresentationJson(13, "Concurrency", "Brian Goetz", of(9, 0), of(10, 0))))
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(error));
  }


  @Test
  public void ifTopicNotCorrectResponseBadRequest() throws Exception
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
  public void ifTimeNotCorrectResponseBadRequest() throws Exception
  {
    final String error = "End time is before start time";
    doThrow(new InvalidPresentationTimeException(error)).when(scheduleService)
        .addPresentation(any(PresentationRequest.class));

    mockMvc.perform(post("/api/v1/schedule/presentations")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content(getPresentationJson(11L, "Concurrency", "Brian Goetz", of(10, 0), of(9, 0))))
        .andExpect(status().isBadRequest())
        .andExpect(status().reason(error));
  }


  @Test
  public void ifTimeIntersectionResponseBadRequest() throws Exception
  {
    final LocalTime startTime = of(9, 0);
    final LocalTime endTime = of(10, 0);
    doThrow(new PresentationTimeIntersectionException("Time intersection")).when(scheduleService)
        .addPresentation(any(PresentationRequest.class));

    mockMvc.perform(post("/api/v1/schedule/presentations")
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .content(getPresentationJson(11L, "Concurrency", "Brian Goetz", startTime, endTime)))
        .andExpect(status().isBadRequest())
        .andExpect(status().reason("Time intersection"));
  }


  @Test
  public void getPresentationById() throws Exception
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
        .andExpect(content().json(getPresentationJson(hallId, topicTitle, topicAuthor, startTime, endTime)));
  }


  @Test
  public void ifPresentationNotExistResponseNotFound() throws Exception
  {
    doReturn(Optional.empty()).when(scheduleService).getPresentation(anyLong());

    mockMvc.perform(get("/api/v1/schedule/presentations/13"))
        .andExpect(status().isNotFound());
  }


  @Test
  public void ifIncorrectIdResponseBadRequest() throws Exception
  {
    doThrow(mock(ConstraintViolationException.class)).when(scheduleService).getPresentation(anyLong());

    mockMvc.perform(get("/api/v1/schedule/presentations/-13"))
        .andExpect(status().isBadRequest());
  }


  @Test
  public void removePresentationById() throws Exception
  {
    mockMvc.perform(delete("/api/v1/schedule/presentations/1000"))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$").doesNotExist());

    verify(scheduleService).deletePresentation(1000L);
  }


  @Test
  public void ifIncorrectIdInDeletionResponseBadRequest() throws Exception
  {
    doThrow(mock(ConstraintViolationException.class))
        .when(scheduleService).deletePresentation(anyLong());

    mockMvc.perform(delete("/api/v1/schedule/presentations/-1000"))
        .andExpect(status().isBadRequest());

    verify(scheduleService).deletePresentation(-1000L);
  }


  @Test
  public void getSchedule() throws Exception
  {
    TopicDto topic = new TopicDto("Concurrency", "Brian Goetz");
    final LocalTime startTime = of(9, 0);
    final LocalTime endTime = of(10, 0);
    PresentationResponse presentation1 = new PresentationResponse(11, topic, startTime, endTime);
    PresentationResponse presentation2 = new PresentationResponse(12, topic, startTime.plusHours(1), endTime.plusHours(1));
    doReturn(asList(presentation1, presentation2)).when(scheduleService).getSchedule();

    String result = mockMvc.perform(get("/api/v1/schedule"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andReturn().getResponse()
        .getContentAsString();

    ScheduleController.ScheduleResponse schedule = objectMapper.readValue(result, ScheduleController.ScheduleResponse.class);

    assertThat(schedule.getPresentations()).isNotEmpty()
        .containsExactlyInAnyOrder(presentation1, presentation2);
  }


  @Test
  public void getEmptySchedule() throws Exception
  {
    doReturn(emptyList()).when(scheduleService).getSchedule();

    mockMvc.perform(get("/api/v1/schedule"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json("{\n" +
            "  \"presentations\" : [ ]\n" +
            "}"));
  }
}
