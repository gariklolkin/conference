package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.api.dto.PlanCancellationRequest;
import com.kyriba.conference.sponsorship.api.dto.PlanRegistrationRequest;
import com.kyriba.conference.sponsorship.service.PlanService;
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
@RequestMapping(value = "/api/v1/sponsorship/plan",
    consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
@Api(value = "Register a new sponsorship plan")
public class PlanController
{
  private final PlanService planService;


  @ApiOperation(value = "Register a new sponsorship plan", response = PlanRegistered.class)
  @PostMapping("/register")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = PlanRegistered.class),
      @ApiResponse(code = 401, message = "Failed", response = String.class)
  })
  ResponseEntity<PlanRegistered> register(@RequestBody PlanRegistrationRequest plan)
  {
    return ResponseEntity.ok(new PlanRegistered(planService.registerPlan(plan).getId()));
  }


  @ApiOperation(value = "Cancel the sponsorship plan", response = PlanCancelled.class)
  @PostMapping("/cancel")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = PlanCancelled.class),
      @ApiResponse(code = 401, message = "Failed", response = String.class)
  })
  ResponseEntity<PlanCancelled> cancel(@RequestBody PlanCancellationRequest plan)
  {
    return ResponseEntity.ok(new PlanCancelled(planService.cancelPlan(plan.getId())));
  }


  @Value
  public static class PlanRegistered
  {
    private String id;
  }


  @Value
  public static class PlanCancelled
  {
    private String id;
  }
}
