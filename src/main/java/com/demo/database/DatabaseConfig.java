package com.demo.database;

import static com.demo.database.DatabaseData.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;



@Configuration
@Component
public class DatabaseConfig {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConfig.class.getName());

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

        //Create a global value for this
        String COLUMN_ID_QUERY = COLUMN_ID + " SERIAL PRIMARY KEY NOT NULL";
        String COLUMN_QUESTION_QUERY = COLUMN_QUESTION + " TEXT NOT NULL";
        String COLUMN_ANSWER_QUERY = COLUMN_ANSWER + " BOOLEAN NOT NULL";
        try {
            conn = dataSource.getConnection();
            LOGGER.log(Level.INFO, COULD_CONNECT);

            stmt = conn.createStatement();

            //String dropTableIfExist = "DROP TABLE IF EXISTS "+TABLE_NAME;
            //stmt.execute(dropTableIfExist);

            try {

                String createTable = ("CREATE TABLE IF NOT EXISTS %s(%s,%s,%s)").formatted(
                        TABLE_NAME,
                        COLUMN_ID_QUERY,
                        COLUMN_QUESTION_QUERY,
                        COLUMN_ANSWER_QUERY);

                stmt.executeUpdate(createTable);
                LOGGER.log(Level.INFO, TABLE_CREATED);

            } catch (SQLException e){
                LOGGER.log(Level.SEVERE, TABLE_NOT_CREATED, e);
                throw e;
            }

            ArrayList <String> Columns = new ArrayList<>(Arrays.asList(COLUMN_ID,COLUMN_QUESTION,COLUMN_ANSWER));
            ResultSet rs = stmt.executeQuery("SELECT id, question, answer FROM " + TABLE_NAME);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();

            ArrayList<String> column= new ArrayList<>();

            for (int i = 1; i<numberOfColumns+1; i++){
                String columnName = rsmd.getColumnName(i);
                column.add(columnName);
                System.out.println("Actuales " + column.get(i-1));
                System.out.println("Nuevas " + Columns.get(i-1));

                //I want to update column names
            }


            //stmt.close();
            conn.close();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, COULD_NOT_CONNECT, e);
        }

        return dataSource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {

        return new JdbcTemplate(dataSource());
    }

}
