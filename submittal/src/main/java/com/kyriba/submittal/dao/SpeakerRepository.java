package com.kyriba.submittal.dao;

import com.kyriba.submittal.domain.Speaker;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


/**
 * @author M-ABL
 */
public interface SpeakerRepository extends CrudRepository<Speaker, Long>
{
  Optional<Speaker> findByEmail(String email);
}
