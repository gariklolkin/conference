package com.kyriba.registration.api;

import com.kyriba.registration.api.dto.AttendeeRegistrationRequest;
import com.kyriba.registration.domain.RegistrationStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Api(value = "Attendee registration endpoint")
@RestController
@RequestMapping("/api/v1/attendees")
public class AttendeeController
{
  /**
   * UCS: Attendee: 1. register in a conference
   *
   * @param attendee input API DTO for new attendee registration
   * @return registered attendee output API DTO
   */
  @ApiOperation(value = "Register new attendee", response = AttendeeRegistrationResponse.class)
  @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  AttendeeRegistrationResponse register(
      @ApiParam(value = "Attendee registration object", required = true) @RequestBody AttendeeRegistrationRequest attendee)
  {
    return new AttendeeRegistrationResponse("123");
  }


  /**
   * UCS: Attendee: 5. see (ticket) registration status
   *
   * @param id attendees id
   * @return registration status output API DTO
   */
  @ApiOperation(value = "Check attendee registration status", response = StatusResponse.class)
  @GetMapping(value = "/{id}/status", produces = APPLICATION_JSON_UTF8_VALUE)
  StatusResponse status(@ApiParam(value = "Attendee identity", required = true) @PathVariable String id)
  {
    return new StatusResponse(RegistrationStatus.REGISTERED);
  }


  @ApiModel(description = "Attendee registration response")
  @Value
  private static class AttendeeRegistrationResponse
  {
    @ApiModelProperty(value = "Identity of registered attendee")
    private final String id;
  }


  @ApiModel(description = "Attendee registration status response")
  @Value
  private static class StatusResponse
  {
    @ApiModelProperty(value = "Attendee registration status message")
    private final String message;


    StatusResponse(RegistrationStatus registrationStatus)
    {
      message = "Your registration status is: " + registrationStatus.getMessage();
    }
  }
}
