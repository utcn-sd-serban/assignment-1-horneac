package ro.utcn.sd.he.assignment1.persistence.api;

import ro.utcn.sd.he.assignment1.model.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository {
    Answer save(Answer answer);

    Optional<Answer> findByID(int id);

    void remove(Answer answer);

    List<Answer> findAll();
}
