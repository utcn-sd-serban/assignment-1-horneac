package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final RepositoryFactory factory;

    @Transactional
    public List<Answer> findAllAnswers() {
        return factory.createAnswerRepository().findAll();
    }

    @Transactional
    public Answer saveAnswer(Answer answer) {
        return factory.createAnswerRepository().save(answer);
    }

    @Transactional
    public List<Answer> findAnswersOf(Question question){
        List<Answer> answers = factory.createAnswerRepository().getAnswersOf(question);
        return answers;
    }

    @Transactional
    public Answer findById(int id){
        return factory.createAnswerRepository().findById(id).get();
    }

    @Transactional
    public void deleteAnswer(int id) {
        Optional<Answer> answer = factory.createAnswerRepository().findById(id);
        factory.createAnswerRepository().remove(answer.get());
    }

}
