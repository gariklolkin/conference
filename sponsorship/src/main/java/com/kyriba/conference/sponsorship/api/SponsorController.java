package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.api.dto.SponsorRegistrationRequest;
import com.kyriba.conference.sponsorship.api.dto.SponsorRegistrationResponse;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import com.kyriba.conference.sponsorship.service.SponsorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "${api.version}/sponsorship/sponsors",
    consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
@Api(value = "Register a new sponsor")
public class SponsorController
{
  private final SponsorService sponsorService;


  @SuppressWarnings("unused")
  @ApiOperation(value = "Register the sponsor")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = SponsorRegistrationResponse.class),
      @ApiResponse(code = 401, message = "Failed to register the sponsor", response = SponsorRegistrationResponse.class)
  })
  SponsorRegistrationResponse register(@Valid @RequestBody SponsorRegistrationRequest request)
  {
    final String randomId = "123";
    final Sponsor sponsor = Sponsor.builder().id(randomId)
        .name(request.getName())
        .email(request.getEmail())
        .build();
    return new SponsorRegistrationResponse(sponsor.getId());
  }
}