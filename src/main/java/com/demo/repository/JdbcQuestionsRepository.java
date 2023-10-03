package com.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcQuestionsRepository implements QuestionsRepository {

    String TABLE_NAME = "QUESTIONS";
    String COLUMN_ID = "ID";
    String COLUMN_QUESTION = "QUESTION";
    String COLUMN_ANSWER = "ANSWER";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Questions questions) {
        return jdbcTemplate.update(
                "INSERT INTO "+TABLE_NAME+"(question, answer) VALUES (?, ?)",
                //questions.getId(), questions.getQuestion(), questions.getAnswer());
                questions.getQuestion(), questions.getAnswer());

    }

    @Override
    public int update(Questions questions) {
        return jdbcTemplate.update(
                "update "+TABLE_NAME+" set question = ? where id = ?",
                questions.getQuestion(), questions.getId());
    }


    @Override
    public int deleteById(Long id) {

        return jdbcTemplate.update("DELETE from " +TABLE_NAME+" where ID = ?", id);
    }

    @Override
    public Optional<Questions> findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select * from " + TABLE_NAME + " where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Questions(
                                rs.getString("question"),
                                rs.getBoolean("answer")
                        ))
        );
    }
    @Override
    public List<Questions> findByQuestion(String question) {
        return jdbcTemplate.query(
                "select * from " + TABLE_NAME +" where question like ?",
                new Object[]{"%" + question},
                (rs, rowNum) ->
                        new Questions(
                                rs.getString("question"),
                                rs.getBoolean("answer")
                        )
        );
    }
    public String getQuestionById(Long id){
        return jdbcTemplate.queryForObject(
                "select question from "+TABLE_NAME+" where id = ?",
                new Object[]{id},
                String.class
        );
    }

}
