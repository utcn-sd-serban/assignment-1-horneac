package ro.utcn.sd.he.assignment1.persistence.memory;

import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.persistence.api.AnswerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAnswerRepository implements AnswerRepository {
    private final Map<Integer, Answer> repo = new ConcurrentHashMap<Integer, Answer>();
    private AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public Answer save(Answer answer) {
        if (answer.getId() == 0) {
            answer.setId(currentId.getAndIncrement());
        }
        repo.put(answer.getId(), answer);
        return answer;
    }

    @Override
    public Optional<Answer> findById(int id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public List<Answer> getAnswersOf(Question question) {
        return answersOf(question);
    }

    @Override
    public void remove(Answer answer) {
        repo.remove(answer.getId());
    }

    @Override
    public List<Answer> findAll() {
        return new ArrayList<Answer>(repo.values());
    }

    private List<Answer> answersOf(Question question) {
        List<Answer> answers = new ArrayList<Answer>();
        for (Answer a : repo.values()) {
            if (a.getQuestionId() == question.getId()) {
                answers.add(a);
            }
        }
        return answers;
    }
}
