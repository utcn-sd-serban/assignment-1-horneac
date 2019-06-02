package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.command.AddQuestionCommand;
import ro.utcn.sd.he.assignment1.dto.QuestionDTO;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionTagService {
    private final QuestionService questionService;
    private final TagService tagService;
    private final RepositoryFactory factory;

    @Transactional
    public List<Question> getQuestionsWithTag(Tag tag) {
        return factory.createQuestionTagRepository().getQuestionsWithTag(tag);
    }

    @Transactional
    public List<Tag> getTagsOfQuestion(Question question) {
        return factory.createQuestionTagRepository().getTagsOfQuestion(question);
    }

    @Transactional
    public void save(Question question, Tag tag) {
        factory.createQuestionTagRepository().insert(question, tag);
    }

    @Transactional
    public void remove(Question question, Tag tag) {
        factory.createQuestionTagRepository().remove(question, tag);
    }

    @Transactional
    public AddQuestionCommand addQuestion(QuestionDTO dto) {
        AddQuestionCommand command = new AddQuestionCommand();
        Question question = new Question(
                dto.getId(), dto.getAuthor(), dto.getTitle(), dto.getText(), dto.getCreation_date_time()
        );
        command.setQuestion(question);
        command.setTags(dto.getTags());
        command.execute(factory);
        return command;
    }

    @Transactional
    public List<Question> filterTag(String tag) {
        Tag tagFound = tagService.getTag(tag);
        return getQuestionsWithTag(tagFound);
    }

}
