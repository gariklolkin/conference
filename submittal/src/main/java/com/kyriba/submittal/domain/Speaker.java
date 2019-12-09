package com.kyriba.submittal.domain;

import com.kyriba.submittal.domain.dto.JobInfo;
import com.kyriba.submittal.domain.dto.PersonalInfo;
import com.kyriba.submittal.domain.dto.SpeakerCreationDto;
import com.kyriba.submittal.domain.dto.TopicInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * @author M-ABL
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
@NoArgsConstructor
@Getter
@Setter
public class Speaker
{
  @Id
  @GeneratedValue
  private Long id;
  @NotBlank
  @Email
  @Size(max = 100)
  private String email;
  @NotNull
  @Enumerated(EnumType.STRING)
  private SpeakerStatus status;
  @Embedded
  private PersonalInfo personalInfo;
  @Embedded
  private JobInfo jobInfo;
  @OneToMany(cascade = CascadeType.ALL)
  private List<Topic> topics;
  @NotBlank
  private String password;


  public Speaker(final SpeakerCreationDto speakerDto)
  {
    setStatus(SpeakerStatus.REGISTRATION);
    setEmail(speakerDto.getEmail());
    // TODO: make a hash of password
    setPassword(speakerDto.getPassword());
    setPersonalInfo(speakerDto.getPersonalInfo());
    setJobInfo(speakerDto.getJobInfo());
    final long conferenceId = speakerDto.getTopicContainer().getConferenceId();
    setTopics(speakerDto.getTopicContainer().getTopics().stream()
        .map(topicInfo -> new Topic(conferenceId, topicInfo, this))
        .collect(toList()));
  }


  public Topic addTopic(final long conferenceId, final TopicInfo topicInfo)
  {
    Topic topic = new Topic(conferenceId, topicInfo, this);
    getTopics().add(topic);
    return topic;
  }


  public void activate()
  {
    setStatus(SpeakerStatus.ACTIVE);
  }
}
