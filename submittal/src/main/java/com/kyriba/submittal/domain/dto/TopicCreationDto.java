package com.kyriba.submittal.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;


/**
 * @author M-ABL
 */
@Data
public class TopicCreationDto
{
  @Positive
  private long conferenceId;
  @NotNull
  @Size(min = 1)
  private List<TopicInfo> topics;
}
