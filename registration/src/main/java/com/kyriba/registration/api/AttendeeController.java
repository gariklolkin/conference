package com.kyriba.registration.api;

import com.kyriba.registration.api.dto.AttendeeRegistrationRequest;
import com.kyriba.registration.domain.RegistrationStatus;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


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
  @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  AttendeeRegistrationResponse register(@RequestBody AttendeeRegistrationRequest attendee)
  {
    return new AttendeeRegistrationResponse("123");
  }


  /**
   * UCS: Attendee: 5. see (ticket) registration status
   *
   * @param id attendees id
   * @return registration status output API DTO
   */
  @GetMapping(value = "/{id}/status", produces = APPLICATION_JSON_UTF8_VALUE)
  StatusResponse status(@PathVariable String id)
  {
    return new StatusResponse(RegistrationStatus.REGISTERED);
  }


  @Value
  private static class AttendeeRegistrationResponse
  {
    private final String id;
  }


  @Value
  private static class StatusResponse
  {
    private final String message;


    StatusResponse(RegistrationStatus registrationStatus)
    {
      message = "Your registration status is: " + registrationStatus.getMessage();
    }
  }
}
