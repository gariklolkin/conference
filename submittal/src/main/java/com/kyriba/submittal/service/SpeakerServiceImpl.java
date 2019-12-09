package com.kyriba.submittal.service;

import com.kyriba.submittal.dao.RegistrationRepository;
import com.kyriba.submittal.dao.SpeakerRepository;
import com.kyriba.submittal.dao.TopicRepository;
import com.kyriba.submittal.domain.Registration;
import com.kyriba.submittal.domain.Speaker;
import com.kyriba.submittal.domain.Topic;
import com.kyriba.submittal.domain.TopicStatus;
import com.kyriba.submittal.domain.dto.CreatedSpeakerDto;
import com.kyriba.submittal.domain.dto.CreatedTopicsDto;
import com.kyriba.submittal.domain.dto.PersonalInfo;
import com.kyriba.submittal.domain.dto.SpeakerCreationDto;
import com.kyriba.submittal.domain.dto.TopicCreationDto;
import com.kyriba.submittal.domain.dto.TopicDto;
import com.kyriba.submittal.service.exception.DuplicateObjectException;
import com.kyriba.submittal.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;


/**
 * @author M-ABL
 */
@Service
@Transactional
@RequiredArgsConstructor
public class SpeakerServiceImpl implements SpeakerService
{
  private final SpeakerRepository speakerRepository;
  private final TopicRepository topicRepository;
  private final RegistrationRepository registrationRepository;
  private final KeyGenerator keyGenerator;


  @Override
  public CreatedSpeakerDto register(final SpeakerCreationDto speakerCreation)
  {
    if (speakerRepository.findByEmail(speakerCreation.getEmail()).isPresent()) {
      throw new DuplicateObjectException("Email is already in use");
    }
    final Speaker speaker = new Speaker(speakerCreation);
    Speaker savedSpeaker = registrationRepository
        .save(new Registration(speaker, keyGenerator.generate(KeyType.EMAIL)))
        .getSpeaker();
    // TODO: NOTIFICATION-MS: send a confirmation email
    return new CreatedSpeakerDto(
        savedSpeaker.getId(),
        new CreatedTopicsDto(savedSpeaker.getTopics().stream().map(Topic::getId).collect(toList())));
  }


  @Override
  public void finishRegistration(final String confirmationKey)
  {
    Registration registration = registrationRepository.findByConfirmationKey(confirmationKey)
        .orElseThrow(() -> new ObjectNotFoundException("The given confirmation key does not exist"));
    Speaker speaker = registration.getSpeaker();
    speaker.activate();
    if (true/*TODO: CONFERENCE-MS: submit topics*/) {
      speaker.getTopics().forEach(topic -> topic.setStatus(TopicStatus.REQUESTED));
    }
    registrationRepository.delete(registration);
  }


  @Override
  @Transactional(readOnly = true)
  public PersonalInfo getPersonalInfo(final long speakerId)
  {
    return findSpeakerById(speakerId).getPersonalInfo();
  }


  @Override
  public void updatePersonalInfo(final long speakerId, final PersonalInfo personalInfo)
  {
    Speaker speaker = findSpeakerById(speakerId);
    speaker.setPersonalInfo(personalInfo);
  }


  @Override
  public CreatedTopicsDto proposeTopics(final long speakerId, final TopicCreationDto topicCreationDto)
  {
    Speaker speaker = findSpeakerById(speakerId);
    final List<Topic> topics = topicCreationDto.getTopics()
        .stream()
        .map(topicInfo -> speaker.addTopic(topicCreationDto.getConferenceId(), topicInfo))
        .collect(toList());
    Iterable<Topic> createdTopics = topicRepository.saveAll(topics);
    if (true/*TODO: CONFERENCE-MS: submit topics*/) {
      createdTopics.forEach(topic -> topic.setStatus(TopicStatus.REQUESTED));
    }
    return new CreatedTopicsDto(StreamSupport
        .stream(createdTopics.spliterator(), false)
        .map(Topic::getId)
        .collect(toList()));
  }


  @Override
  @Transactional(readOnly = true)
  public List<TopicDto> getTopics(final long speakerId)
  {
    Speaker speaker = findSpeakerById(speakerId);
    return speaker.getTopics()
        .stream()
        .map(Topic::toDto)
        .collect(toList());
  }


  @Override
  @Transactional(readOnly = true)
  public List<TopicDto> getTopics(final long speakerId, final long conferenceId)
  {
    Speaker speaker = findSpeakerById(speakerId);
    return topicRepository.findAllBySpeakerAndConferenceId(speaker, conferenceId)
        .stream()
        .map(Topic::toDto)
        .collect(toList());
  }


  private Speaker findSpeakerById(final long speakerId)
  {
    return speakerRepository.findById(speakerId)
        .orElseThrow(() -> new ObjectNotFoundException("Speaker does not exist"));
  }
}
