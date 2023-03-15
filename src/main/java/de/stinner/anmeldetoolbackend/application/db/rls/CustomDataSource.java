package de.stinner.anmeldetoolbackend.application.db.rls;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
public class CustomDataSource {

    private final DataSourceProperties properties;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new TenantAwareHikariDataSource();

        dataSource.setInitializationFailTimeout(0);
        dataSource.setMaximumPoolSize(5);

        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());

        return dataSource;
    }

}
