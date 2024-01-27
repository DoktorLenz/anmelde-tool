package dev.stinner.scoutventure.keycloak;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EmbeddedKeycloakDatasourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.keycloak")
    public DataSourceProperties keycloakDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource keycloakDataSource() {
        return keycloakDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
