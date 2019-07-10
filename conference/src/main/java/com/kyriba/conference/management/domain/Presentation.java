package com.kyriba.conference.management.domain;


import com.kyriba.conference.management.domain.dto.PresentationRequest;
import com.kyriba.conference.management.domain.dto.PresentationResponse;
import com.kyriba.conference.management.domain.dto.TopicDto;
import com.kyriba.conference.management.domain.exception.InvalidPresentationTimeException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalTime;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = { "title", "author" })
)
public class Presentation
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private Hall hall;
  @Embedded
  private Topic topic;
  private LocalTime startTime;
  private LocalTime endTime;


  public Presentation(PresentationRequest request)
  {
    checkNotNull(request);

    update(request);
  }


  public void update(PresentationRequest request)
  {
    checkNotNull(request);
    checkTime(request.getEndTime(), request.getStartTime());

    topic = new Topic(request.getTopic());
    startTime = request.getStartTime();
    endTime = request.getEndTime();
  }


  private void checkTime(LocalTime endTime, LocalTime startTime)
  {
    if (endTime.isBefore(startTime))
      throw new InvalidPresentationTimeException("End time is before start time");
  }


  public PresentationResponse toDto()
  {
    checkState(hall != null && topic != null);

    return new PresentationResponse(hall.getId(), new TopicDto(topic), startTime, endTime);
  }
}
