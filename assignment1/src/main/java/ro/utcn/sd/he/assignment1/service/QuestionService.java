package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final RepositoryFactory factory ;
    private final TagService tagService;

    @Transactional
    public List<Question> listQuestions(){return factory.createQuestionRepository().findAll();}

    @Transactional
    public Question saveQuestion(Question question){
        return factory.createQuestionRepository().save(question);
    }

    public void deleteQuestion(int id){
        Optional<Question> question = factory.createQuestionRepository().findById(id);
        factory.createQuestionRepository().remove(question.get());
    }


}
