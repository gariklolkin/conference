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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


  @ApiOperation(value = "Show all conference halls", response = HallResponse.class, responseContainer = "List")
  @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
  List<HallResponse> findAllHalls()
  {
    return hallService.findAll().stream()
        .map(HallResponse::new)
        .collect(Collectors.toList());
  }


  @ApiOperation(value = "Create conference hall", response = HallResponse.class)
  @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  HallResponse createHall(@ApiParam(value = "Hall creation object", required = true) HallRequest hall)
  {
    return new HallResponse(hallService.createHall(hall));
  }


  @ApiOperation(value = "Change conference hall parameters", response = HallResponse.class)
  @PutMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
  HallResponse updateHall(@ApiParam(value = "Hall identity", required = true) @PathVariable String id,
                          @ApiParam(value = "Hall change object", required = true) HallRequest hall)
  {
    return new HallResponse(hallService.updateHall(Long.valueOf(id), hall));
  }


  @ApiOperation(value = "Remove conference hall", response = HallResponse.class)
  @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
  HallResponse removeHall(@ApiParam(value = "Hall identity", required = true) @PathVariable String id)
  {
    hallService.deleteHall(Long.valueOf(id));
    return new HallResponse();
  }
}
