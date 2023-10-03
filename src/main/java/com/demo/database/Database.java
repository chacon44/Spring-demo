package com.demo.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.sql.Statement;
import java.util.logging.Logger;


@Configuration
@Component
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

        Connection conn;
        Statement stmt;

        //Table parameters

        String TABLE_NAME = "QUESTIONS";
        String COLUMN_ID = "ID SERIAL PRIMARY KEY NOT NULL";
        String COLUMN_QUESTION = "QUESTION TEXT NOT NULL";
        String COLUMN_ANSWER = "ANSWER BOOLEAN NOT NULL";
        try {
            conn = dataSource.getConnection();
            LOGGER.log(Level.INFO, "Connection established");

            stmt = conn.createStatement();

            String dropTableIfExist = "DROP TABLE IF EXISTS "+TABLE_NAME;

            String createTable = "CREATE TABLE "+ TABLE_NAME+
                    " ("+COLUMN_ID +", "+ COLUMN_QUESTION +", "+ COLUMN_ANSWER +")";

            stmt.execute(dropTableIfExist);
            stmt.executeUpdate(createTable);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Could not establish connection", e);
            System.exit(0);
        }
        LOGGER.log(Level.INFO, "Table created successfully");

        return dataSource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {

        return new JdbcTemplate(dataSource());
    }

}
