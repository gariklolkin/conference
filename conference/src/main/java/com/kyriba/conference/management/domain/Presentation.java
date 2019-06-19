package com.kyriba.conference.management.domain;


import com.kyriba.conference.management.domain.exception.InvalidPresentationTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalTime;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Presentation
{
  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  private Hall hall;
  @Embedded
  private Topic topic;
  private LocalTime startTime;
  private LocalTime endTime;


  public void validate()
  {
    if (endTime.isBefore(startTime))
      throw new InvalidPresentationTime("End time is before start time");
  }
}
