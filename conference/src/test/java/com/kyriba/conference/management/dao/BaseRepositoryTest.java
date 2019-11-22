package com.kyriba.conference.management.dao;


import com.github.database.rider.core.DBUnitRule;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("persistencetest")
public class BaseRepositoryTest
{
  @Autowired
  protected JdbcTemplate jdbcTemplate;

  @Rule
  public DBUnitRule dbUnitRule = DBUnitRule.instance(() -> jdbcTemplate.getDataSource().getConnection());

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Autowired
  protected TestEntityManager em;

}
