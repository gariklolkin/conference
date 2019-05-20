package com.kyriba.conference.management.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description="Request model for hall operation action")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallRequest
{
  @ApiModelProperty(value = "Name or number of the hall")
  private String name;

  @ApiModelProperty(value = "Hall place count")
  private Integer places;
}
