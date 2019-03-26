package ro.utcn.sd.he.assignment1.persistence.api;

import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository {
    Answer save(Answer answer);

    Optional<Answer> findById(int id);

    List<Answer> getAnswersOf(Question question);


    void remove(Answer answer);

    List<Answer> findAll();
}
