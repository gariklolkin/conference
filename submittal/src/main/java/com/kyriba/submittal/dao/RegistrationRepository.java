package com.kyriba.submittal.dao;

import com.kyriba.submittal.domain.Registration;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


/**
 * @author M-ABL
 */
public interface RegistrationRepository extends CrudRepository<Registration, Long>
{
  Optional<Registration> findByConfirmationKey(String key);
}
