package com.kyriba.submittal.service;

import com.kyriba.submittal.domain.Topic;
import com.kyriba.submittal.domain.TopicStatus;
import org.springframework.lang.NonNull;


/**
 * @author M-ABL
 */
public interface TopicService
{
  /**
   * Approves or rejects a {@link Topic topic} with the given {@code topicId}
   *
   * @param topicId an id of the topic to transit
   * @param status a new {@link TopicStatus status}
   */
  void transit(long topicId, @NonNull TopicStatus status);
}
