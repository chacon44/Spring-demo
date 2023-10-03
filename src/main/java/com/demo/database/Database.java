package com.demo.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@Configuration
public class Database {

    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

    final String DRIVER = "org.postgresql.Driver";
    final String HOST = "localhost";
    final int port = 5433;
    final String DATABASE_NAME = "postgres";
    final String URL = "jdbc:postgresql://"+HOST+":"+port+"/"+DATABASE_NAME;
    final String USERNAME = "postgres";
    final String PASSWORD = "1";

    @Bean
    public DataSource dataSource() throws IllegalStateException {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        try (Connection conn = dataSource.getConnection()) {
            LOGGER.log(Level.INFO, "Connection established");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not establish connection", e);
        }

        return dataSource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {

        return new JdbcTemplate(dataSource());
    }
}
