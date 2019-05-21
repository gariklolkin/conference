package com.kyriba.conference.management.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;


@ApiModel(description = "Request model for presentation operation action")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresentationRequest
{
  @ApiModelProperty(value = "Hall identity")
  private Long hall;

  @ApiModelProperty(value = "Presentation topic")
  private TopicDto topic;

  @ApiModelProperty(value = "Presentation start time")
  @JsonFormat(pattern = "HH:mm")
  private LocalTime startTime;

  @ApiModelProperty(value = "Presentation end time")
  @JsonFormat(pattern = "HH:mm")
  private LocalTime endTime;
}
