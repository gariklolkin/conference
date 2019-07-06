package com.kyriba.submittal.domain.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.kyriba.submittal.domain.TopicStatus;
import lombok.AllArgsConstructor;
import lombok.Value;


/**
 * @author M-ABL
 */
@AllArgsConstructor
@Value
public class TopicDto
{
  private long topicId;
  private TopicStatus status;
  @JsonUnwrapped
  private TopicInfo info;
  private long conferenceId;
}
