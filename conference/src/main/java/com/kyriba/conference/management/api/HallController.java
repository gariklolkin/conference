package com.kyriba.conference.management.api;


import com.kyriba.conference.management.api.dto.HallRequest;
import com.kyriba.conference.management.api.dto.HallResponse;
import com.kyriba.conference.management.service.HallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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


  @ApiOperation(value = "Show all conference halls", responseContainer = "List")
  @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
  List<HallResponse> findAllHalls()
  {
    return hallService.findAll().stream()
        .map(HallResponse::new)
        .collect(Collectors.toList());
  }


  @ApiOperation(value = "Create conference hall")
  @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  Long createHall(@Valid @ApiParam(value = "Hall creation object", required = true) @RequestBody HallRequest hall)
  {
    return hallService.createHall(hall).getId();
  }


  @ApiOperation(value = "Change conference hall parameters")
  @PutMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  HallResponse updateHall(@Valid @ApiParam(value = "Hall identity", required = true) @PathVariable String id,
                          @Valid @ApiParam(value = "Hall change object", required = true) @RequestBody HallRequest hall)
  {
    return new HallResponse(hallService.updateHall(Long.valueOf(id), hall));
  }


  @ApiOperation(value = "Remove conference hall")
  @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  HallResponse removeHall(@Valid @ApiParam(value = "Hall identity", required = true) @PathVariable String id)
  {
    hallService.deleteHall(Long.valueOf(id));
    return new HallResponse();
  }
}
