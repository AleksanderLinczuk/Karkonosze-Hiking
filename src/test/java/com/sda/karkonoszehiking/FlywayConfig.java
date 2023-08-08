package com.sda.karkonoszehiking;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {

    private final DataSource dataSource;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        FluentConfiguration fluentConfiguration = Flyway.configure().dataSource(dataSource).baselineOnMigrate(true)
                .locations("classpath:db/testmigration");
        return new Flyway(fluentConfiguration);
    }
}