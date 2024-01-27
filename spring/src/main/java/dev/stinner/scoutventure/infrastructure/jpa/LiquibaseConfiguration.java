package dev.stinner.scoutventure.infrastructure.jpa;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfiguration {
    @Value("${sv.liquibase.change-log}")
    private String liquibaseChangeLog;
    
    @Bean
    public SpringLiquibase liquibase(@Qualifier("scoutVentureDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(liquibaseChangeLog);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }
}
