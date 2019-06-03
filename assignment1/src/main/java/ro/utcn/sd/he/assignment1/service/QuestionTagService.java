package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.command.AddQuestionCommand;
import ro.utcn.sd.he.assignment1.dto.QuestionDTO;
import ro.utcn.sd.he.assignment1.dto.QuestionVoteDTO;
import ro.utcn.sd.he.assignment1.event.QuestionCreatedEvent;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionTagService {
    private final QuestionService questionService;
    private final TagService tagService;
    private final RepositoryFactory factory;
    private final ApplicationEventPublisher eventPublisher;
    private final VoteService voteService;

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
        QuestionVoteDTO questionVoteDTO = QuestionVoteDTO.ofEntity(command.getQuestion(), voteService.getVoteCount(command.getQuestion()));
        eventPublisher.publishEvent(new QuestionCreatedEvent(questionVoteDTO));
        return command;
    }

    @Transactional
    public List<QuestionVoteDTO> filterTag(String tag) {
        Tag tagFound = tagService.getTag(tag);
        List<Question> questions = getQuestionsWithTag(tagFound);
        return questions.stream().map(question ->
                QuestionVoteDTO.ofEntity(
                        question,
                        factory.createVoteRepository().getVoteCount(question))).collect(Collectors.toList());

    }

}
