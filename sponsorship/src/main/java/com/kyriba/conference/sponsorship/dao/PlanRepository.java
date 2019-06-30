package com.kyriba.conference.sponsorship.dao;

import com.kyriba.conference.sponsorship.domain.Plan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Aliaksandr Samal
 */
@Repository
public interface PlanRepository extends CrudRepository<Plan, Long>
{
}
