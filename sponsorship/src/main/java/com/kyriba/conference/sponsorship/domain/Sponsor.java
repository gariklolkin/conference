package com.kyriba.conference.sponsorship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author M-ASL
 * @since v1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sponsor
{
  private String id;
  private String name;
  private String email;
}
