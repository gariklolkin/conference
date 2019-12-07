package com.kyriba.conference.sponsorship.api;

import com.kyriba.conference.sponsorship.domain.PlanCategory;
import com.kyriba.conference.sponsorship.domain.dto.PlanDto;
import com.kyriba.conference.sponsorship.service.exception.ObjectNotFoundException;
import com.kyriba.conference.sponsorship.service.PlanService;
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
import javax.validation.constraints.NotNull;


/**
 * @author M-ASL
 * @since v1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "${api.version.path}/plans", produces = { MediaType.APPLICATION_JSON_VALUE })
@Api(value = "Register a new sponsorship plan")
public class PlanController
{
  private final PlanService planService;


  @ApiOperation(value = "Register the plan")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 401, message = "Failed to register the plan")
  })
  @PostMapping
  public PlanRegistrationResponse register(@Valid @RequestBody PlanRegistrationRequest request)
  {
    return new PlanRegistrationResponse(planService.createPlan(request.getCategory(), request.getSponsorEmail()));
  }


  @ApiOperation(value = "Cancel the plan")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "OK"),
      @ApiResponse(code = 401, message = "Failed to cancel the plan")
  })
  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void cancel(@ApiParam(value = "Id of the plan to cancel", required = true) @PathVariable Long id)
  {
    planService.deletePlan(id);
  }


  @ApiOperation(value = "Get the plan")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 404, message = "Plan not found")
  })
  @GetMapping("/{id}")
  public PlanDto get(@ApiParam(value = "Id of the plan to get", required = true) @PathVariable Long id)
  {
    return planService.readPlan(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plan Not Found"));
  }


  @ExceptionHandler(ObjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  void handleEmptyResultDataAccessException() { }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static final class PlanRegistrationRequest
  {
    private @NotNull PlanCategory category;
    private @Email String sponsorEmail;
  }


  @Value
  private static class PlanRegistrationResponse
  {
    private long id;
  }
}
