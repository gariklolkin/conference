package com.kyriba.conference.management.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


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
  @JsonInclude(NON_EMPTY)
  private Long hall;

  @ApiModelProperty(value = "Presentation topic")
  @JsonInclude(NON_EMPTY)
  private TopicRequestResponse topic;

  @ApiModelProperty(value = "Presentation start time")
  @JsonFormat(pattern = "HH:mm")
  @JsonInclude(NON_NULL)
  private LocalTime startTime;

  @ApiModelProperty(value = "Presentation end time")
  @JsonFormat(pattern = "HH:mm")
  @JsonInclude(NON_NULL)
  private LocalTime endTime;
}
