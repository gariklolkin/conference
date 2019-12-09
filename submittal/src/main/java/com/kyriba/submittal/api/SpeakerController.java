package com.kyriba.submittal.api;

import com.kyriba.submittal.domain.dto.CreatedSpeakerDto;
import com.kyriba.submittal.domain.dto.CreatedTopicsDto;
import com.kyriba.submittal.domain.dto.PersonalInfo;
import com.kyriba.submittal.domain.dto.SpeakerCreationDto;
import com.kyriba.submittal.domain.dto.TopicCreationDto;
import com.kyriba.submittal.domain.dto.TopicDto;
import com.kyriba.submittal.service.SpeakerService;
import com.kyriba.submittal.service.exception.DuplicateObjectException;
import com.kyriba.submittal.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


/**
 * @author M-ABL
 */
@RestController
@RequestMapping(value = "${api.version.path}/speakers",
    consumes = APPLICATION_JSON_UTF8_VALUE,
    produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class SpeakerController
{
  private final SpeakerService speakerService;


  @ExceptionHandler(ObjectNotFoundException.class)
  void handleObjectNotFoundException(final HttpServletResponse response,
                                     final ObjectNotFoundException e) throws IOException
  {
    response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
  }


  @ExceptionHandler(DuplicateObjectException.class)
  void handleDuplicateObjectException(final HttpServletResponse response,
                                      final DuplicateObjectException e) throws IOException
  {
    response.sendError(HttpStatus.CONFLICT.value(), e.getMessage());
  }


  @PostMapping(path = "/register")
  CreatedSpeakerDto register(@Valid @RequestBody SpeakerCreationDto speaker)
  {
    return speakerService.register(speaker);
  }


  @PostMapping(path = "/{speakerId}/topics")
  CreatedTopicsDto proposeTopics(@PathVariable long speakerId,
                                 @Valid @RequestBody TopicCreationDto topicRequest)
  {
    return speakerService.proposeTopics(speakerId, topicRequest);
  }


  @GetMapping(path = "/{speakerId}/topics")
  List<TopicDto> getTopics(@PathVariable long speakerId)
  {
    return speakerService.getTopics(speakerId);
  }


  @GetMapping(path = "/{speakerId}/conferences/{conferenceId}/topics")
  List<TopicDto> getTopics(@PathVariable long speakerId, @PathVariable long conferenceId)
  {
    return speakerService.getTopics(speakerId, conferenceId);
  }


  @GetMapping(path = "/{speakerId}/personal")
  PersonalInfo getPersonalInfo(@PathVariable long speakerId)
  {
    return speakerService.getPersonalInfo(speakerId);
  }


  @PutMapping(path = "/{speakerId}/personal")
  void updatePersonalInfo(@PathVariable long speakerId,
                          @Valid @RequestBody PersonalInfo personalInfo)
  {
    speakerService.updatePersonalInfo(speakerId, personalInfo);
  }

}
