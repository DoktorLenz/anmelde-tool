package dev.stinner.scoutventure.infrastructure.jpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"dev.stinner.scoutventure.infrastructure.jpa"},
        entityManagerFactoryRef = "scoutVentureEntityManagerFactory",
        transactionManagerRef = "scoutVentureTransactionManager"
)
public class ScoutVentureJpaConfiguration {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean scoutVentureEntityManagerFactory(
            @Qualifier("scoutVentureDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder
    ) {
        return builder.dataSource(dataSource).packages("dev.stinner.scoutventure.infrastructure.jpa.models").build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager scoutVentureTransactionManager(
            @Qualifier("scoutVentureEntityManagerFactory") LocalContainerEntityManagerFactoryBean scoutVentureEntityManagerFactory
    ) {
        return new JpaTransactionManager(Objects.requireNonNull(scoutVentureEntityManagerFactory.getObject()));
    }
}
