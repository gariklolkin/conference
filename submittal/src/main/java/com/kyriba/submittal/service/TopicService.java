package com.kyriba.submittal.service;

import com.kyriba.submittal.domain.Topic;


/**
 * @author M-ABL
 */
public interface TopicService
{

  /**
   * Approves a {@link Topic topic } with the  given {@code id}
   *
   * @param topicId an id of the topic to approve
   */
  // TODO: NOTIFICATION-MS: notify a speaker that a topic was approved
  void approve(long topicId);


  /**
   * Rejects a {@link Topic topic } with the  given {@code id}
   *
   * @param topicId an id of the topic to approve
   */
  // TODO: NOTIFICATION-MS: notify a speaker that a topic was rejected
  void reject(long topicId);
}
