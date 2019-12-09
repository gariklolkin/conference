package com.kyriba.submittal.domain;

import com.kyriba.submittal.domain.dto.TopicDto;
import com.kyriba.submittal.domain.dto.TopicInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * @author M-ABL
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Topic
{
  @Id
  @GeneratedValue
  private Long id;
  @Embedded
  private TopicInfo info;
  private long conferenceId;
  @Enumerated(EnumType.STRING)
  private TopicStatus status;
  @ManyToOne(targetEntity = Speaker.class)
  @JoinColumn(name = "speaker_id")
  private Speaker speaker;


  public Topic(final long conferenceId, final TopicInfo info, final Speaker speaker)
  {
    this.info = info;
    this.conferenceId = conferenceId;
    this.status = TopicStatus.DRAFT;
    this.speaker = speaker;
  }


  public TopicDto toDto()
  {
    return new TopicDto(id, status, info, conferenceId);
  }

}
