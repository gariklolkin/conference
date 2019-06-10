package com.kyriba.conference.registration.api;


import com.kyriba.conference.registration.api.dto.ChangeTicketOwnerRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Api(value = "Ticket management endpoint")
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
  @ApiOperation(value = "Change ticket owner to an other attendee")
  @PutMapping(value = "/{id}/owner", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  void exchange(
      @Valid @ApiParam(value = "Ticket identity", required = true) @PathVariable String id,
      @Valid @ApiParam(value = "Ticket change owner object", required = true) @RequestBody ChangeTicketOwnerRequest exchange)
  {

  }


  @ApiModel(description = "Ticket response")
  @Value
  private static class TicketResponse
  {
    @ApiModelProperty(value = "The identity of ticket")
    private final String id;
  }
}
