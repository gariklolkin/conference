package com.kyriba.conference.management.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@ApiModel(description="Request model for hall operation action")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallRequest
{
  @ApiModelProperty(value = "Name or number of the hall")
  @NotBlank
  private String name;

  @ApiModelProperty(value = "Hall place count")
  @NotNull
  @Min(10)
  private Integer places;
}
