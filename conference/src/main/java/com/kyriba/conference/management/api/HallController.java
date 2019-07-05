package com.kyriba.conference.management.api;


import com.kyriba.conference.management.domain.dto.HallRequest;
import com.kyriba.conference.management.domain.dto.HallResponse;
import com.kyriba.conference.management.service.HallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Api(value = "Hall management endpoint")
@RestController
@RequestMapping("/api/v1/halls")
@AllArgsConstructor
@SuppressWarnings("unused")
public class HallController
{
  private final HallService hallService;


  @ApiOperation(value = "Get a conference hall if exists")
  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  HallResponse findHall(@Valid @ApiParam(value = "Hall identity", required = true) @PathVariable long id)
  {
    return hallService.findHall(id);
  }


  @ApiOperation(value = "Show all conference halls", responseContainer = "List")
  @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
  List<HallResponse> findAllHalls()
  {
    return hallService.findAllHalls();
  }


  @ApiOperation(value = "Create conference hall")
  @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  long createHall(
      @Valid @ApiParam(value = "Hall creation object", required = true) @RequestBody HallRequest hallRequest)
  {
    return hallService.createHall(hallRequest);
  }


  @ApiOperation(value = "Change conference hall parameters")
  @PutMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  void updateHall(@Valid @ApiParam(value = "Hall identity", required = true) @PathVariable long id,
                  @Valid @ApiParam(value = "Hall change object", required = true) @RequestBody HallRequest hallRequest)
  {
    hallService.updateHall(id, hallRequest);
  }


  @ApiOperation(value = "Remove conference hall")
  @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(value = NO_CONTENT)
  void removeHall(@Valid @ApiParam(value = "Hall identity", required = true) @PathVariable long id)
  {
    hallService.removeHall(id);
  }


  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(value = CONFLICT, reason = "Hall with the same name already exists.")
  void handleDuplicateKeyException()
  {
  }


  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(value = CONFLICT, reason = "Cannot remove Hall because it is used.")
  void handleInUseException()
  {
  }
}
