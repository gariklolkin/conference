package com.kyriba.conference.api.dto;

import com.kyriba.conference.domain.Hall;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description="Response model for hall operation action")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HallResponse
{
  @ApiModelProperty(value = "Hall identity")
  private Long id;


  public HallResponse(Hall hall)
  {
    id = hall.getId();
  }
}
