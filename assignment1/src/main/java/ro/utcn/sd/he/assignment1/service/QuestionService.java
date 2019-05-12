package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final RepositoryFactory factory;

    @Transactional
    public List<Question> listQuestions() {
        List<Question> questions = factory.createQuestionRepository().findAll();
        questions.sort(Comparator.comparing(Question::getCreation_date_time).reversed());
        return questions;
    }

    @Transactional
    public Question findById(int id) {
        Optional<Question> question = factory.createQuestionRepository().findById(id);
        return question.orElse(null);
    }

    @Transactional
    public List<Question> searchTitle(String title) {
        List<Question> questions = listQuestions().stream()
                .filter((q1) -> q1.getTitle().contains(title))
                .collect(Collectors.toList());
        return questions;
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
