package com.kyriba.conference.sponsorship.dao;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.api.DBRider;
import com.kyriba.conference.sponsorship.domain.Sponsor;
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
class SponsorRepositoryTest
{
  @Autowired
  private SponsorRepository sponsorRepository;
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  protected JdbcTemplate jdbcTemplate;

  private ConnectionHolder connectionHolder = () -> jdbcTemplate.getDataSource().getConnection();


  @Test
  @DataSet("datasets/empty.xml")
  @ExpectedDataSet("datasets/sponsor-created.xml")
  void sponsorMayBeCreated()
  {
    final Sponsor sponsor = new Sponsor();
    sponsor.setName("Test_name");
    sponsor.setEmail("Test_email");
    sponsorRepository.save(sponsor);
    // Despite of documentation for JUnit 5, extensions ordering doesn't work as expected.
    // To make it work the transaction is committed as the last statement in test
    entityManager.getEntityManager().getTransaction().commit();
  }


  @Test
  @DataSet("datasets/sponsor-read.xml")
  void sponsorMayBeRead()
  {
    Assertions.assertNotNull(sponsorRepository.findByEmail("Test_email"));
  }
}
