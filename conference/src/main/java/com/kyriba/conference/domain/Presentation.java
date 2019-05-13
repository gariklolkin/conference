package com.kyriba.conference.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalTime;


//@Entity
@Getter
@ToString
@EqualsAndHashCode
public class Presentation
{
  private Long id;
  private Hall hall;
  private Topic topic;
  private LocalTime startTime;
  private LocalTime endTime;
}
