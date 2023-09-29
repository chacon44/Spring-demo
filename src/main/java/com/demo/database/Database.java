package com.demo.database;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class Database {

    DataSource dataSource;

    @Bean
    public DataSource dataSource() throws IllegalStateException, SQLException {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgres://localhost:5433/postgres");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("1");

        dataSource = dataSourceBuilder.build();

        return dataSource;
    }



    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate();
    }
}
