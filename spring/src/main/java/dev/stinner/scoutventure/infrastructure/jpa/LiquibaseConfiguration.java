package dev.stinner.scoutventure.infrastructure.jpa;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfiguration {
    @Bean
    public SpringLiquibase liquibase(@Qualifier("scoutVentureDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/liquibase-changelog.xml");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
