package com.demo.repository;

import com.demo.controllers.RestController;
import com.demo.dto.ResponseDTO;
import com.demo.model.AnsweredQuestion;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import static com.demo.database.DatabaseData.*;

@Repository
public class JdbcQuestionsRepository implements QuestionsRepository {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(String question, boolean answer) {

        String saveQuery = ("INSERT INTO %s " +
                "(%s,%s) VALUES (?, ?)").formatted(TABLE_NAME, COLUMN_QUESTION, COLUMN_ANSWER);

        if (findByQuestion(question).isEmpty())
            return jdbcTemplate.update(saveQuery, question, answer) > 0;
        else return false;
    }

    @Override
    public boolean updateAnswer(long Id, boolean answer) {

        String query = ("UPDATE %s SET %s = ? WHERE %s = ?").formatted(TABLE_NAME, COLUMN_ANSWER, COLUMN_ID);
        return jdbcTemplate.update(query, answer, Id) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = ("DELETE from %s WHERE %s = ?").formatted(TABLE_NAME, COLUMN_ID);

        return jdbcTemplate.update(query, id) > 0;
    }

    @Override
    public Optional<AnsweredQuestion> findById(Long id) {
        String query = ("SELECT * from %s WHERE %s = ?").formatted(TABLE_NAME, COLUMN_ID);
        try {
            AnsweredQuestion response = jdbcTemplate.queryForObject(query, (resultSet, rowNum) ->
                            new AnsweredQuestion(
                                    resultSet.getString(COLUMN_QUESTION),
                                    resultSet.getBoolean(COLUMN_ANSWER)
                            ),
                    id
            );
            logger.info("Finished search");
            return Optional.ofNullable(response);
        } catch (EmptyResultDataAccessException e) {
            logger.info("No rows found");
            return Optional.empty();
        }
    }

    @Override
    public Optional <ResponseDTO> returnQuestion(Long id) {
        String query = ("SELECT * from %s WHERE %s = ?").formatted(TABLE_NAME, COLUMN_ID);

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, (resultSet, rowNum) ->
                        (new ResponseDTO(
                                id,
                                resultSet.getString(COLUMN_QUESTION),
                                resultSet.getBoolean(COLUMN_ANSWER)
                        )),
                id
        ));
    }

    @Override
    public Optional<ResponseDTO> findByQuestion(String question) {

        String query = "SELECT * from " + TABLE_NAME + " WHERE " + COLUMN_QUESTION + " = '" + question + "'";

        List<ResponseDTO> responses = jdbcTemplate.query(
                query,
                (resultSet, i) -> new ResponseDTO(
                        resultSet.getLong(COLUMN_ID),
                        question,
                        resultSet.getBoolean(COLUMN_ANSWER)
                )
        );

        if (responses.isEmpty()) {
            return Optional.empty();
        } else {

            return Optional.of(responses.get(0));
        }
    }

    @Override
    public Optional <ResponseDTO> returnIdByQuestion(String question) {

        String query = "SELECT * from " + TABLE_NAME + " WHERE " + COLUMN_QUESTION + " = '" + question + "'";

        List<ResponseDTO> responses = jdbcTemplate.query(
                query,
                (resultSet, i) -> new ResponseDTO(
                        resultSet.getLong(COLUMN_ID),
                        question,
                        resultSet.getBoolean(COLUMN_ANSWER)
                )
        );
            return Optional.ofNullable(responses.get(0));
    }


}
