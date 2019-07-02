package com.kyriba.conference.sponsorship.api;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;


/**
 * @author Aliaksandr Samal
 */
abstract class AbstractContainerBaseTest
{
  private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER;

  static {
    POSTGRE_SQL_CONTAINER = new PostgreSQLContainer()
        .withDatabaseName("sponsorship_db")
        .withUsername("postgres")
        .withPassword("postgres");
    POSTGRE_SQL_CONTAINER.start();
  }


  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
  {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext)
    {
      TestPropertyValues.of(
          "spring.datasource.url=" + POSTGRE_SQL_CONTAINER.getJdbcUrl(),
          "spring.datasource.username=" + POSTGRE_SQL_CONTAINER.getUsername(),
          "spring.datasource.password=" + POSTGRE_SQL_CONTAINER.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
    }
  }
}
