package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.api.dto.PlanRegistered;
import com.kyriba.conference.sponsorship.api.dto.PlanRegistrationRequest;
import com.kyriba.conference.sponsorship.domain.Plan;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import com.kyriba.conference.sponsorship.service.PlanService;
import com.kyriba.conference.sponsorship.service.SponsorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;


/**
 * @author M-ASL
 * @since v1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.version}/sponsorship/plans",
    consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
@Api(value = "Register a new sponsorship plan")
public class PlanController
{
  private final PlanService planService;
  private final SponsorService sponsorService;


  @SuppressWarnings("unused")
  @ApiOperation(value = "Register the plan")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = PlanRegistered.class),
      @ApiResponse(code = 401, message = "Failed to register the plan", response = PlanRegistered.class)
  })
  PlanRegistered register(@Valid @RequestBody PlanRegistrationRequest request)
  {
    Sponsor sponsor = sponsorService.readByEmail(request.getSponsorEmail());
    Plan plan = planService.createPlan(request.getCategory(), sponsor);
    final String stringId = Optional.ofNullable(plan)
        .map(Plan::getId)
        .map(String::valueOf)
        .orElse(null);
    return new PlanRegistered(stringId);
  }


  @SuppressWarnings("unused")
  @ApiOperation(value = "Cancel the plan")
  @PutMapping("/{id}/cancellation")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 401, message = "Failed to cancel the plan")
  })
  void cancel(@ApiParam(value = "Id of the plan to cancel", required = true) @PathVariable String id)
  {
    planService.deletePlan(Long.valueOf(id));
  }


  @SuppressWarnings("unused")
  @ApiOperation(value = "Get the plan")
  @GetMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = PlanRegistered.class),
      @ApiResponse(code = 401, message = "Failed to get the plan", response = PlanRegistered.class)
  })
  PlanRegistered get(@ApiParam(value = "Id of the plan to get", required = true) @PathVariable String id)
  {
    final Plan plan = planService.readPlan(Long.valueOf(id));
    final String stringId = Optional.ofNullable(plan)
        .map(Plan::getId)
        .map(String::valueOf)
        .orElse(null);
    return new PlanRegistered(stringId);
  }


  @SuppressWarnings("unused")
  @ExceptionHandler(EmptyResultDataAccessException.class)
  public void handleDuplicateKeyException(HttpServletResponse response) throws IOException
  {
    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }
}
