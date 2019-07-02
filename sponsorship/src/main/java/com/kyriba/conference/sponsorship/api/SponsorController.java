package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.domain.dto.SponsorDto;
import com.kyriba.conference.sponsorship.service.SponsorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


/**
 * @author M-ASL
 * @since v1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.version.path}/sponsorship/sponsors",
    consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
@Api(value = "Register a new sponsor")
public class SponsorController
{
  private final SponsorService sponsorService;


  @ApiOperation(value = "Register the sponsor")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 401, message = "Failed to register the sponsor")
  })
  @PostMapping
  SponsorRegistrationResponse register(@Valid @RequestBody SponsorRegistrationRequest request)
  {
    return new SponsorRegistrationResponse(sponsorService.createSponsor(request.getName(), request.getEmail()));
  }


  @ApiOperation(value = "Get the sponsor")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 404, message = "Sponsor not found")
  })
  @GetMapping("/{id}")
  SponsorDto get(@ApiParam(value = "Id of the sponsor to get", required = true) @PathVariable Long id)
  {
    return sponsorService.readSponsor(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sponsor Not Found"));
  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static final class SponsorRegistrationRequest
  {
    private @NotEmpty String name;
    private @Email String email;
  }


  @Value
  private static class SponsorRegistrationResponse
  {
    private long id;
  }
}
