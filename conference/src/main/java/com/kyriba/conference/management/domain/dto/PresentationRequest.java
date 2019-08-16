package com.kyriba.conference.management.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;


@ApiModel(description = "Request model for presentation operation action")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PresentationRequest
{
  @ApiModelProperty(value = "Hall identity")
  @NotNull
  private long hall;

  @ApiModelProperty(value = "Presentation topic")
  @NotNull
  private TopicDto topic;

  @ApiModelProperty(value = "Presentation start time")
  @JsonFormat(pattern = "HH:mm")
  @NotNull
  private LocalTime startTime;

  @ApiModelProperty(value = "Presentation end time")
  @JsonFormat(pattern = "HH:mm")
  @NotNull
  private LocalTime endTime;
}
