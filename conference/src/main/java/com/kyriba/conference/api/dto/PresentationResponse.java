package com.kyriba.conference.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@ApiModel(description = "Response model on a presentation operation action")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PresentationResponse
{
  @ApiModelProperty(value = "Presentation identity")
  private Long id;

  @ApiModelProperty(value = "Hall identity")
  private Long hall;

  @ApiModelProperty(value = "Presentation topic")
  private TopicRequestResponse topic;

  @ApiModelProperty(value = "Presentation start time")
  private LocalTime startTime;

  @ApiModelProperty(value = "Presentation end time")
  private LocalTime endTime;
}
