package com.kyriba.submittal.api;

import com.kyriba.submittal.service.TopicService;
import com.kyriba.submittal.service.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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


  @PostMapping(path = "/{topicId}/approve")
  void approve(@PathVariable long topicId)
  {
    topicsService.approve(topicId);
  }


  @PostMapping(path = "/{topicId}/reject")
  void reject(@PathVariable long topicId)
  {
    topicsService.reject(topicId);
  }

}
