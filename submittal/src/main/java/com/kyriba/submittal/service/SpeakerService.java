package com.kyriba.submittal.service;

import com.kyriba.submittal.domain.Speaker;
import com.kyriba.submittal.domain.dto.JobInfo;
import com.kyriba.submittal.domain.dto.PersonalInfo;
import com.kyriba.submittal.domain.dto.TopicDto;
import com.kyriba.submittal.domain.dto.TopicInfo;
import org.springframework.data.util.Pair;

import java.util.List;


/**
 * @author M-ABL
 */
public interface SpeakerService
{
  /**
   * Creates a new speaker and his {@code topics} for the given {@code conferenceId}.
   *
   * @return an id of just created user
   */
  // TODO: NOTIFICATION-MS: send a confirmation email
  Pair<Long, List<Long>> register(String email,
                                  String password,
                                  PersonalInfo personalInfo,
                                  JobInfo jobInfo,
                                  List<TopicInfo> topics,
                                  long conferenceId);

  /**
   * Completes the speaker's registration if the given {@code key} is correct.
   */
  // TODO: CONFERENCE-MS: submit topics
  void finishRegistration(String confirmationKey);

  /**
   * Returns a {@link PersonalInfo personal info} of the {@link Speaker}
   *
   * @return personal info
   */
  PersonalInfo getPersonalInfo(long speakerId);

  /**
   * Updates a {@link PersonalInfo personal info} of the {@link Speaker}
   */
  void updatePersonalInfo(long speakerId, PersonalInfo personalInfo);

  /**
   * Adds {@code topics} of the given {@code conferenceId} for the {@link Speaker}
   *
   * @return a list of created topics
   */
  List<Long> proposeTopics(long speakerId, List<TopicInfo> topics, long conferenceId);

  /**
   * Returns a list of {@link TopicDto topics} belong to the given {@code speakerId}
   *
   * @param speakerId a speaker
   * @return the list of topics
   */
  List<TopicDto> getTopics(long speakerId);

  /**
   * Returns a list of {@link TopicDto topics} belong to the given {@code speakerId} and {@code conferenceId}
   *
   * @param speakerId a speaker
   * @param conferenceId an external id of conference
   * @return the list of topics
   */
  List<TopicDto> getTopics(long speakerId, long conferenceId);
}
