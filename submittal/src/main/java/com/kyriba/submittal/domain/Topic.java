package com.kyriba.submittal.domain;

import com.kyriba.submittal.domain.dto.TopicDto;
import com.kyriba.submittal.domain.dto.TopicInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @author M-ABL
 */
@NoArgsConstructor
@Getter
@Setter
public class Topic
{
  private Long id;
  private TopicInfo info;
  private long conferenceId;
  private TopicStatus status;
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


  public void approve()
  {
    this.status = TopicStatus.APPROVED;
  }


  public void reject()
  {
    this.status = TopicStatus.REJECTED;
  }
}
