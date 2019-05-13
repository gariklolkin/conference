package com.kyriba.registration.api;


import com.kyriba.registration.api.dto.ChangeTicketOwnerRequest;
import lombok.Value;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController
{

  /**
   * UCS: Attendee: 4. change attendee
   *
   * @param exchange input API DTO for exchanging ticket
   * @return ticket output API DTO
   */
  @PatchMapping(value = "/{id}/exchange", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  TicketResponse exchange(@PathVariable String id, @RequestBody ChangeTicketOwnerRequest exchange)
  {
    return new TicketResponse("324");
  }


  @Value
  private static class TicketResponse
  {
    private final String id;
  }
}
