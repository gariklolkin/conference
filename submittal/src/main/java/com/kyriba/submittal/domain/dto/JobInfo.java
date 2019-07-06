package com.kyriba.submittal.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;


/**
 * @author M-ABL
 */
@Getter
@Setter
public class JobInfo
{
  @Size(max = 50)
  private String position;
  @Size(max = 50)
  private String company;
  @Size(max = 20)
  private String city;
  @Size(max = 20)
  private String country;
}
