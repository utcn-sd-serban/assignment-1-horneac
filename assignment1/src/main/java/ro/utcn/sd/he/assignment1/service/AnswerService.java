package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.utcn.sd.he.assignment1.command.AddAnswerCommand;
import ro.utcn.sd.he.assignment1.command.DeleteAnswerCommand;
import ro.utcn.sd.he.assignment1.command.EditAnswerCommand;
import ro.utcn.sd.he.assignment1.dto.AnswerVoteDTO;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final RepositoryFactory factory;

    @Transactional
    public List<Answer> findAllAnswers() {
        return factory.createAnswerRepository().findAll();
    }

    @Transactional
    public AddAnswerCommand saveAnswer(Answer answer) {
        AddAnswerCommand command = new AddAnswerCommand(answer);
        command.execute(factory);
        return command;
    }

    @Transactional
    public EditAnswerCommand editAnswer(Answer newAnswer) {
        EditAnswerCommand command = new EditAnswerCommand();
        command.setNewAnswer(newAnswer);
        command.execute(factory);
        return command;

    }

    @Transactional
    public List<AnswerVoteDTO> findAnswersOf(Question question) {
        List<Answer> answers = factory.createAnswerRepository().getAnswersOf(question);
        answers.sort(new CustomComparator().reversed());
        return answers.stream().map(answer -> {
            return AnswerVoteDTO.ofEntity(answer, factory.createVoteRepository().getVoteCount(answer));
        }).collect(Collectors.toList());
    }

    @Transactional
    public Answer findById(int id) {
        return factory.createAnswerRepository().findById(id).get();
    }

    @Transactional
    public DeleteAnswerCommand deleteAnswer(int id) {
        Answer answer = factory.createAnswerRepository().findById(id).get();
        DeleteAnswerCommand command = new DeleteAnswerCommand(answer);
        command.execute(factory);
        return command;
    }

    protected class CustomComparator implements Comparator<Answer> {
        public int compare(Answer a1, Answer a2) {
            return factory.createVoteRepository().getVoteCount(a1) - factory.createVoteRepository().getVoteCount(a2);
        }
    }

}
