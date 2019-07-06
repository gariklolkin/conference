package com.kyriba.submittal.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.kyriba.submittal.domain.dto.JobInfo;
import com.kyriba.submittal.domain.dto.PersonalInfo;
import com.kyriba.submittal.domain.dto.TopicDto;
import com.kyriba.submittal.domain.dto.TopicInfo;
import com.kyriba.submittal.service.SpeakerService;
import com.kyriba.submittal.service.exception.DuplicateObjectException;
import com.kyriba.submittal.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.springframework.data.util.Pair;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
  SpeakerRegistrationResponse register(@Valid @RequestBody SpeakerRequest speaker)
  {
    Pair<Long, List<Long>> speakerAndTopics = speakerService.register(speaker.email, speaker.password,
        speaker.personalInfo, speaker.jobInfo, speaker.topicContainer.topics, speaker.topicContainer.conferenceId);
    return new SpeakerRegistrationResponse(speakerAndTopics.getFirst(), speakerAndTopics.getSecond());
  }


  @PostMapping(path = "/{speakerId}/topics")
  List<Long> proposeTopics(@PathVariable long speakerId,
                           @Valid @RequestBody TopicRequest topicRequest)
  {
    return speakerService.proposeTopics(speakerId, topicRequest.topics, topicRequest.conferenceId);
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


  @Setter
  private static class TopicRequest
  {
    @Positive
    private long conferenceId;
    @NotNull
    @Size(min = 1)
    private List<TopicInfo> topics;
  }

  @Setter
  private static class SpeakerRequest
  {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private PersonalInfo personalInfo;
    @NotNull
    private JobInfo jobInfo;
    @NotNull
    @JsonUnwrapped
    private TopicRequest topicContainer;
  }

  @Value
  private static class SpeakerRegistrationResponse
  {
    private long speakerId;
    private List<Long> topics;
  }
}
