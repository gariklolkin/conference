package com.kyriba.submittal.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;


/**
 * @author M-ABL
 */
@Embeddable
@Getter
@Setter
public class TopicInfo
{
  @Size(min = 10, max = 100)
  private String title;
  @Size(max = 1000)
  private String description;

}
