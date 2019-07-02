package com.kyriba.conference.sponsorship.dao;

import com.kyriba.conference.sponsorship.domain.Sponsor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.stream.StreamSupport;


/**
 * @author Aliaksandr Samal
 */
public interface SponsorRepository extends CrudRepository<Sponsor, Long>
{
  @SuppressWarnings("unused")
  @Nullable
  default Sponsor readByEmail(String email)
  {
    return StreamSupport.stream(findAll().spliterator(), false)
        .filter(x -> email.equals(x.getEmail()))
        .findFirst()
        .orElse(null);
  }
}
