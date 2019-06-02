package com.kyriba.conference.management.api;


import com.kyriba.conference.management.api.dto.PresentationRequest;
import com.kyriba.conference.management.api.dto.PresentationResponse;
import com.kyriba.conference.management.api.dto.ScheduleResponse;
import com.kyriba.conference.management.api.dto.TopicDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

import static java.time.LocalTime.of;
import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Api(value = "Conference schedule management endpoint")
@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController
{

  @ApiOperation(value = "Show conference schedule")
  @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
  ScheduleResponse showSchedule()
  {
    return ScheduleResponse.builder().presentations(buildPresentations()).build();
  }


  @ApiOperation(value = "Add presentation in conference schedule")
  @PostMapping(value = "/presentations", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  Long addPresentation(
      @ApiParam(value = "Presentation create object", required = true) @RequestBody PresentationRequest presentation)
  {
    return 55L;
  }


  @ApiOperation(value = "View presentation information")
  @GetMapping(value = "/presentations/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  PresentationResponse getPresentation(
      @ApiParam(value = "Presentation identity", required = true) @PathVariable String id)
  {
    return PresentationResponse.builder()
        .hall(123L)
        .startTime(LocalTime.of(11, 30))
        .endTime(LocalTime.of(12, 30))
        .topic(new TopicDto("Spring Data REST", "Andy Wilkinson"))
        .build();
  }


  @ApiOperation(value = "Remove presentation from conference schedule")
  @DeleteMapping(value = "/presentations/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
  void removePresentation(
      @ApiParam(value = "Presentation identity", required = true) @PathVariable String id)
  {

  }


  @ApiOperation(value = "Change presentations parameters")
  @PutMapping(value = "/presentations/{id}", consumes = APPLICATION_JSON_UTF8_VALUE)
  void updatePresentation(
      @ApiParam(value = "Presentation identity", required = true) @PathVariable String id,
      @ApiParam(value = "Presentation change object", required = true) @RequestBody PresentationRequest presentation)
  {

  }


  private List<PresentationResponse> buildPresentations()
  {
    return asList(
        PresentationResponse.builder()
            .hall(101L)
            .topic(new TopicDto("Spring Data REST", "Andy Wilkinson"))
            .startTime(of(10, 00))
            .endTime(of(11, 15))
            .build(),
        PresentationResponse.builder()
            .hall(101L)
            .topic(new TopicDto("Microservices in practice", "Mikalai Alimenkou"))
            .startTime(of(11, 45))
            .endTime(of(15, 00))
            .build(),
        PresentationResponse.builder()
            .hall(122L)
            .topic(new TopicDto("All about Spring workshop", "Ivan Ivanou"))
            .startTime(of(10, 0))
            .endTime(of(15, 15))
            .build()
    );
  }
}
