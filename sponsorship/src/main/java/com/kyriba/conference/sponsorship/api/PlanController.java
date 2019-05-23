package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.api.dto.PlanRegistrationRequest;
import com.kyriba.conference.sponsorship.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping(value = "/api/v1/sponsorship/plans",
    consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
@Api(value = "Register a new sponsorship plan")
public class PlanController
{
  private final PlanService planService;


  @SuppressWarnings("unused")
  @ApiOperation(value = "Register a new sponsorship plan")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 401, message = "Failed to register a new plan", response = String.class)
  })
  PlanRegistered register(@Valid @RequestBody PlanRegistrationRequest plan)
  {
    return new PlanRegistered(planService.registerPlan(plan).getId());
  }


  @SuppressWarnings("unused")
  @ApiOperation(value = "Cancel the sponsorship plan")
  @PutMapping("/{id}/cancellation")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = String.class),
      @ApiResponse(code = 401, message = "Failed to cancel the plan", response = PlanRegistered.class)
  })
  PlanRegistered cancel(@ApiParam(value = "Id of the plan to cancel", required = true) @PathVariable String id)
  {
    return new PlanRegistered(planService.cancelPlan(id));
  }


  @SuppressWarnings("unused")
  @ApiOperation(value = "Get the sponsorship plan")
  @GetMapping("/{id}")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = PlanRegistered.class),
      @ApiResponse(code = 401, message = "Failed to cancel the plan", response = String.class)
  })
  PlanRegistered get(@ApiParam(value = "Id of the plan to get", required = true) @PathVariable String id)
  {
    return new PlanRegistered(id);
  }


  @Value
  public static class PlanRegistered
  {
    private String id;
  }
}
