package com.kyriba.conference.sponsorship.dao;

import com.kyriba.conference.sponsorship.domain.Plan;
import org.springframework.data.repository.CrudRepository;


/**
 * @author Aliaksandr Samal
 */
public interface PlanRepository extends CrudRepository<Plan, Long>
{
}
