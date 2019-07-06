package com.kyriba.submittal.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * @author M-ABL
 */
@Getter
@Setter
public class PersonalInfo
{
  @Size(max = 20)
  private String title;
  @NotBlank
  @Size(max = 100)
  private String firstName;
  @NotBlank
  @Size(max = 100)
  private String lastName;
  @Size(max = 20)
  private String phone;
}
