package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final RepositoryFactory factory;

    @Transactional
    public List<Question> listQuestions() {
        List<Question> questions =  factory.createQuestionRepository().findAll();
        questions.sort(Comparator.comparing(Question::getCreation_date_time).reversed());
        return questions;
    }

    public Question findById(int id) {
        return factory.createQuestionRepository().findById(id).get();
    }

    /*

    @param question -> a question object to save in the repository
     */
    @Transactional
    public Question saveQuestion(Question question) {
        return factory.createQuestionRepository().save(question);
    }

    @Transactional
    public void deleteQuestion(int id) {
        Optional<Question> question = factory.createQuestionRepository().findById(id);
        factory.createQuestionRepository().remove(question.get());
    }


}
