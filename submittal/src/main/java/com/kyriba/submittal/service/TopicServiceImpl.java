package com.kyriba.submittal.service;

import com.kyriba.submittal.dao.TopicRepository;
import com.kyriba.submittal.domain.Topic;
import com.kyriba.submittal.domain.TopicStatus;
import com.kyriba.submittal.service.exception.ObjectNotFoundException;
import com.kyriba.submittal.service.exception.UnsupportedTopicTransitionException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author M-ABL
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService
{
  private final TopicRepository topicRepository;


  @Override
  public void transit(final long topicId, @NonNull final TopicStatus status)
  {
    Topic topic = findTopicById(topicId);
    if (topic.getStatus() == TopicStatus.DRAFT) {
      throw new ObjectNotFoundException("The requested topic is not ready");
    }
    switch (status) {
      case APPROVED:
        // TODO: notify a topic has been approved
        break;
      case REJECTED:
        // TODO: notify a topic has been rejected
        break;
      default:
        throw new UnsupportedTopicTransitionException("A topic cannot be moved to the status " + status);
    }
    topic.setStatus(status);
  }


  private Topic findTopicById(final long topicId)
  {
    return topicRepository.findById(topicId)
        .orElseThrow(() -> new ObjectNotFoundException("Topic does not exist"));
  }
}
