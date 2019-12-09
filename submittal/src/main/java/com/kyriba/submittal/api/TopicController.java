package com.kyriba.submittal.api;

import com.kyriba.submittal.domain.TopicStatus;
import com.kyriba.submittal.service.TopicService;
import com.kyriba.submittal.service.exception.ObjectNotFoundException;
import com.kyriba.submittal.service.exception.UnsupportedTopicTransitionException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


/**
 * @author M-ABL
 */
@RestController
@RequestMapping(value = "${api.version.path}/topics",
    consumes = APPLICATION_JSON_UTF8_VALUE,
    produces = APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class TopicController
{
  private final TopicService topicsService;


  @ExceptionHandler(ObjectNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  void handleObjectNotFound()
  {
  }


  @ExceptionHandler(UnsupportedTopicTransitionException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  void handleUnsupportedTopicTransition()
  {
  }


  @PutMapping(path = "/{topicId}/status")
  void changeStatus(@PathVariable long topicId, @Valid @RequestBody ChangeStatusRequest statusRequest)
  {
    topicsService.transit(topicId, statusRequest.status);
  }


  @Setter
  private static class ChangeStatusRequest
  {
    @NotNull
    private TopicStatus status;
  }
}
