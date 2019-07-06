package com.kyriba.submittal.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author M-ABL
 */
@NoArgsConstructor
@Getter
@Setter
public class Registration
{
  private Long id;
  private Speaker speaker;
  private String confirmationKey;


  public Registration(final Speaker speaker, final String confirmationKey)
  {
    this.speaker = speaker;
    this.confirmationKey = confirmationKey;
  }
}
