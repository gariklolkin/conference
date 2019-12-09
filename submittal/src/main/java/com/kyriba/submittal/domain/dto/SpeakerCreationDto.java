package com.kyriba.submittal.domain.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @author M-ABL
 */
@Data
public class SpeakerCreationDto
{
  @NotBlank
  private String email;
  @NotBlank
  private String password;
  @NotNull
  private PersonalInfo personalInfo;
  @NotNull
  private JobInfo jobInfo;
  @NotNull
  @JsonUnwrapped
  private TopicCreationDto topicContainer;
}
