package com.kyriba.conference.sponsorship.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessage
{
  private List<Recipient> recipients;
  private String body;
  private String subject;
  private Set<Options> emailMessageOptions;


  @SuppressWarnings("unused")
  public enum Options
  {
    HIGH_PRIORITY, HIDE_ADDRESS
  }
}
