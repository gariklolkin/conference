package com.kyriba.conference.sponsorship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Value
@AllArgsConstructor
@Builder
class Recipient
{
  private String name;
  private String address;
}
