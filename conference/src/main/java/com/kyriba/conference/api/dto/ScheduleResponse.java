package com.kyriba.conference.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@ApiModel(description = "Conference schedule response model")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResponse
{
  @ApiModelProperty(value = "List of presentation")
  private List<PresentationResponse> presentations;
}
