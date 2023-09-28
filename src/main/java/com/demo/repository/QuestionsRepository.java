package com.demo.repository;

import com.demo.database.EntityQuestion;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class QuestionsRepository implements DemoRepository<EntityQuestion> {

    // Using an in-memory Map
    // to store the object data
    private final Map<Long, EntityQuestion> repository;

    public QuestionsRepository() {
        this.repository = new HashMap<>();
    }

    // Implementation for save method
    @Override
    public void save(EntityQuestion entityQuestion) {
        repository.put(entityQuestion.getId(), entityQuestion);
    }

    @Override
    public EntityQuestion findQuestionById(Long id) {
        return repository.get(id);
    }
}