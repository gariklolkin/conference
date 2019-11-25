package com.kyriba.conference.sponsorship.dao;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.kyriba.conference.sponsorship.domain.Plan;
import com.kyriba.conference.sponsorship.domain.PlanCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;


/**
 * @author Aliaksandr Samal
 */
@DataJpaTest
@DBRider
@ActiveProfiles("test")
public class PlanRepositoryTest
{
  @Autowired
  private PlanRepository planRepository;
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  protected JdbcTemplate jdbcTemplate;

  private ConnectionHolder connectionHolder = () -> jdbcTemplate.getDataSource().getConnection();

  @Test
  @DataSet("datasets/plan-empty.xml")
  @ExpectedDataSet("datasets/plan-created.xml")
  void planMayBeCreated()
  {
    final Plan plan = new Plan();
    plan.setCategory(PlanCategory.STARTER);
    planRepository.save(plan);
    // Despite of documentation for JUnit 5, extensions ordering doesn't work as expected.
    // To make it work the transaction is committed as the last statement in test
    entityManager.getEntityManager().getTransaction().commit();
  }


  @Test
  @DataSet("datasets/plan-read.xml")
  void planMayBeRead()
  {
    Assertions.assertNotNull(planRepository.findById(5L));
  }
}

