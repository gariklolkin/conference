package com.kyriba.submittal.domain.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Value;


/**
 * @author M-ABL
 */
@Value
public class CreatedSpeakerDto
{
  private long speakerId;
  @JsonUnwrapped
  private CreatedTopicsDto topics;
}
