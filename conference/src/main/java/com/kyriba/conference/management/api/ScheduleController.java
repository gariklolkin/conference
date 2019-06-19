package com.kyriba.conference.management.api;


import com.kyriba.conference.management.api.dto.PresentationRequest;
import com.kyriba.conference.management.api.dto.PresentationResponse;
import com.kyriba.conference.management.api.dto.ScheduleResponse;
import com.kyriba.conference.management.api.dto.TopicDto;
import com.kyriba.conference.management.domain.Presentation;
import com.kyriba.conference.management.domain.exception.EntityNotFound;
import com.kyriba.conference.management.domain.exception.LinkedEntityNotFound;
import com.kyriba.conference.management.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Api(value = "Conference schedule management endpoint")
@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController
{
  private final ScheduleService scheduleService;


  @Autowired
  public ScheduleController(ScheduleService scheduleService)
  {
    this.scheduleService = scheduleService;
  }


  @ApiOperation(value = "Show conference schedule")
  @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
  ScheduleResponse showSchedule()
  {
    List<PresentationResponse> presentations = stream(scheduleService.getSchedule().spliterator(), false)
        .map(this::toPresentationResponse)
        .collect(Collectors.toList());
    return ScheduleResponse.builder().presentations(presentations).build();
  }


  @ApiOperation(value = "Add presentation in conference schedule")
  @PostMapping(value = "/presentations", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  Long addPresentation(
      @ApiParam(value = "Presentation create object", required = true) @RequestBody PresentationRequest presentationRequest) throws
      LinkedEntityNotFound
  {
    return scheduleService.addPresentation(presentationRequest);
  }


  @ApiOperation(value = "View presentation information")
  @GetMapping(value = "/presentations/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<PresentationResponse> getPresentation(
      @ApiParam(value = "Presentation identity", required = true) @PathVariable Long id)
  {
    return ResponseEntity.of(scheduleService.getPresentation(id).map(this::toPresentationResponse));
  }


  @ApiOperation(value = "Remove presentation from conference schedule")
  @DeleteMapping(value = "/presentations/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(value = NO_CONTENT)
  void removePresentation(
      @ApiParam(value = "Presentation identity", required = true) @PathVariable Long id)
  {
    scheduleService.deletePresentation(id);
  }


  @ApiOperation(value = "Change presentations parameters")
  @PutMapping(value = "/presentations/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
  void updatePresentation(
      @ApiParam(value = "Presentation identity", required = true) @PathVariable Long id,
      @ApiParam(value = "Presentation change object", required = true) @RequestBody PresentationRequest presentationRequest) throws
      EntityNotFound, LinkedEntityNotFound
  {
    scheduleService.updatePresentation(id, presentationRequest);
  }


  private PresentationResponse toPresentationResponse(Presentation presentation)
  {
    return PresentationResponse.builder()
        .topic(new TopicDto(presentation.getTopic()))
        .hall(presentation.getHall().getId())
        .startTime(presentation.getStartTime())
        .endTime(presentation.getEndTime())
        .build();
  }
}
