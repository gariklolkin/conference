package com.kyriba.submittal.service;

import com.kyriba.submittal.domain.Speaker;
import com.kyriba.submittal.domain.dto.CreatedSpeakerDto;
import com.kyriba.submittal.domain.dto.CreatedTopicsDto;
import com.kyriba.submittal.domain.dto.PersonalInfo;
import com.kyriba.submittal.domain.dto.SpeakerCreationDto;
import com.kyriba.submittal.domain.dto.TopicCreationDto;
import com.kyriba.submittal.domain.dto.TopicDto;

import java.util.List;


/**
 * @author M-ABL
 */
public interface SpeakerService
{
  /**
   * Creates a new speaker and his {@code topics} for the given {@code conferenceId}.
   *
   * @return {@link CreatedSpeakerDto dto} that contains id of created speaker and ids of created topics
   */
  CreatedSpeakerDto register(SpeakerCreationDto speakerCreation);

  /**
   * Completes the speaker's registration if the given {@code key} is correct.
   */
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
   * @return {@link CreatedTopicsDto dto} that contains ids of created topics
   */
  CreatedTopicsDto proposeTopics(long speakerId, TopicCreationDto topicCreationDto);

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
