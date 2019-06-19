package com.kyriba.conference.management.api;


import com.kyriba.conference.management.api.dto.HallRequest;
import com.kyriba.conference.management.api.dto.HallResponse;
import com.kyriba.conference.management.domain.exception.EntityNotFound;
import com.kyriba.conference.management.service.HallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@Api(value = "Hall management endpoint")
@RestController
@RequestMapping("/api/v1/halls")
public class HallController
{
  private final HallService hallService;


  @Autowired
  public HallController(HallService hallService)
  {
    this.hallService = hallService;
  }


  @ApiOperation(value = "Get a conference hall if exists")
  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<HallResponse> findHall(
      @Valid @ApiParam(value = "Hall identity", required = true) @PathVariable Long id)
  {
    return ResponseEntity.of(hallService.findHall(id));
  }


  @ApiOperation(value = "Show all conference halls", responseContainer = "List")
  @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
  List<HallResponse> findAllHalls()
  {
    return hallService.findAllHalls();
  }


  @ApiOperation(value = "Create conference hall")
  @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  Long createHall(
      @Valid @ApiParam(value = "Hall creation object", required = true) @RequestBody HallRequest hallRequest)
  {
    return hallService.createHall(hallRequest);
  }


  @ApiOperation(value = "Change conference hall parameters")
  @PutMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  void updateHall(@Valid @ApiParam(value = "Hall identity", required = true) @PathVariable Long id,
                  @Valid @ApiParam(value = "Hall change object", required = true) @RequestBody HallRequest hallRequest) throws
      EntityNotFound
  {
    hallService.updateHall(id, hallRequest);
  }


  @ApiOperation(value = "Remove conference hall")
  @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  @ResponseStatus(value = NO_CONTENT)
  void removeHall(@Valid @ApiParam(value = "Hall identity", required = true) @PathVariable Long id)
  {
    hallService.removeHall(id);
  }
}
