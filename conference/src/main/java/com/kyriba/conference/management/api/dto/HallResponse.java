package com.kyriba.conference.management.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kyriba.conference.management.domain.Hall;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;


@ApiModel(description = "Response model for hall operation action")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallResponse
{
  @ApiModelProperty(value = "Name or number of the hall")
  @JsonInclude(NON_EMPTY)
  private String name;

  @ApiModelProperty(value = "Hall place count")
  @JsonInclude(NON_EMPTY)
  private Integer places;


  public HallResponse(Hall hall)
  {
    name = hall.getName();
    places = hall.getPlaces();
  }
}
