package ro.utcn.sd.he.assignment1.persistence.memory;

import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.api.QuestionRepository;

import java.util.*;

public class InMemoryQuestionRepository implements QuestionRepository {
    private final Map<Integer, Question> data = new HashMap<>();
    private int currentId = 1;

    @Override
    public synchronized Question save(Question question) {
        if (question.getId() != 0) {
            data.put(question.getId(), question);
        } else {
            question.setId(currentId++);
            data.put(question.getId(), question);

        }
        return question;
    }

    @Override
    public Optional<Question> findById(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(Question question) {
        data.remove(question.getId());
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(data.values());
    }
}
