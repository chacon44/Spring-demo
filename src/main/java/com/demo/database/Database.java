package com.demo.database;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class Database {

    DataSource dataSource;

    @Bean
    public DataSource dataSource() throws IllegalStateException {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgres://localhost:5433/postgres");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("1");

        dataSource = dataSourceBuilder.build();
        return dataSource;
    }

//    @Bean
//    public PGSimpleDataSource pgSimpleDataSource() {
//
//        PGSimpleDataSource ds = new PGSimpleDataSource();
//        ds.setServerName("localhost");
//        ds.setPortNumber(5433);
//        ds.setDatabaseName("postgres");
//        ds.setUser("postgres");
//        ds.setPassword("1");
//
//        try {
//            Connection conn = ds.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return ds;
//    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
}
