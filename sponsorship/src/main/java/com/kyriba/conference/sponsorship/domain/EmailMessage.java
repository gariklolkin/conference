package com.kyriba.conference.sponsorship.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;


@Data
@RequiredArgsConstructor
public class EmailMessage
{
  private final List<Recipient> recipients;
  private final String body;
  private final String subject;
  private Set<Options> emailMessageOptions;


  @SuppressWarnings("unused")
  public enum Options
  {
    HIGH_PRIORITY, HIDE_ADDRESS
  }

}
