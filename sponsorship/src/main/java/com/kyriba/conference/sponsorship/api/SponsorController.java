package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.api.dto.SponsorRegistrationRequest;
import com.kyriba.conference.sponsorship.service.SponsorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author M-ASL
 * @since v1.0
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/api/v1/sponsorship/sponsor",
    consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
@Api(value = "Register a new sponsor")
public class SponsorController
{
  private final SponsorService sponsorService;


  @ApiOperation(value = "Register a new sponsor", response = SponsorRegistered.class)
  @PostMapping("/register")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = SponsorRegistered.class),
      @ApiResponse(code = 401, message = "Failed", response = String.class)
  })
  ResponseEntity<SponsorRegistered> register(@RequestBody SponsorRegistrationRequest sponsor)
  {
    return ResponseEntity.ok(new SponsorRegistered(sponsorService.registerSponsor(sponsor).getId()));
  }


  @Value
  public static class SponsorRegistered
  {
    private String id;
  }
}
