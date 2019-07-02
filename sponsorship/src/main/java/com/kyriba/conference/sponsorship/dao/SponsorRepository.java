package com.kyriba.conference.sponsorship.dao;

import com.kyriba.conference.sponsorship.domain.Sponsor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


/**
 * @author Aliaksandr Samal
 */
public interface SponsorRepository extends CrudRepository<Sponsor, Long>
{
  Optional<Sponsor> findByEmail(String email);
}
