package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.domain.dto.SponsorDto;
import com.kyriba.conference.sponsorship.service.SponsorService;
import com.kyriba.conference.sponsorship.service.exception.ObjectNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@RequestMapping(value = "${api.version.path}/sponsors", produces = { MediaType.APPLICATION_JSON_VALUE })
@Tag(name = "Register a new sponsor")
public class SponsorController
{
  private final SponsorService sponsorService;


  @Operation(summary = "Register the sponsor")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "401", description = "Failed to register the sponsor")
  })
  @PostMapping
  public SponsorRegistrationResponse register(@Valid @RequestBody SponsorRegistrationRequest request)
  {
    return new SponsorRegistrationResponse(sponsorService.createSponsor(request.getName(), request.getEmail()));
  }


  @Operation(summary = "Get the sponsor")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "404", description = "Sponsor not found")
  })
  @GetMapping("/{id}")
  public SponsorDto get(@Parameter(name = "Id of the sponsor to get", required = true) @PathVariable Long id)
  {
    return sponsorService.readSponsor(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sponsor Not Found"));
  }


  @Operation(summary = "Delete the sponsor")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "OK"),
      @ApiResponse(responseCode = "401", description = "Failed to delete the sponsor")
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void cancel(@Parameter(name = "Id of the sponsor to delete", required = true) @PathVariable Long id)
  {
    sponsorService.deleteSponsor(id);
  }


  @ExceptionHandler(ObjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  void handleEmptyResultDataAccessException() { }


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
