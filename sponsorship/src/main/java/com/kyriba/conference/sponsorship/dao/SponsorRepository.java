package com.kyriba.conference.sponsorship.dao;

import com.kyriba.conference.sponsorship.domain.Sponsor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Aliaksandr Samal
 */
@Repository
public interface SponsorRepository extends CrudRepository<Sponsor, Long>
{
}
