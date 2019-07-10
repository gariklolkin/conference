package com.kyriba.submittal.dao;

import com.kyriba.submittal.domain.Speaker;
import com.kyriba.submittal.domain.Topic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * @author M-ABL
 */
public interface TopicRepository extends CrudRepository<Topic, Long>
{
  List<Topic> findAllBySpeakerAndConferenceId(Speaker speaker, long conferenceId);
}
