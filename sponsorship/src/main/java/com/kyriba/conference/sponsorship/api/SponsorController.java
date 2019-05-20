package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.api.dto.SponsorRegistrationRequest;
import com.kyriba.conference.sponsorship.service.SponsorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author M-ASL
 * @since v1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/sponsorship/sponsors",
    consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
@Api(value = "Register a new sponsor")
public class SponsorController
{
  private final SponsorService sponsorService;


  @SuppressWarnings("unused")
  @ApiOperation(value = "Register a new sponsor")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = SponsorRegistered.class),
      @ApiResponse(code = 401, message = "Failed", response = String.class)
  })
  SponsorRegistered register(@Valid @RequestBody SponsorRegistrationRequest sponsor)
  {
    return new SponsorRegistered(sponsorService.registerSponsor(sponsor).getId());
  }


  @Value
  public static class SponsorRegistered
  {
    private String id;
  }
}
