package ro.utcn.sd.he.assignment1.persistence.memory;

import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.persistence.api.QuestionTagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryQuestionTagRepository implements QuestionTagRepository {
    private final Map<Question, Tag> repo = new ConcurrentHashMap<>();

    @Override
    public void insert(Question question, Tag tag) {
        repo.put(question, tag);
    }

    @Override
    public List<Question> getQuestionsWithTag(Tag tag) {
        List<Question> questions = new ArrayList<>();
        for (Question q : repo.keySet()) {
            if (repo.get(q).equals(tag)) {
                questions.add(q);
            }
        }
        return questions;
    }

    @Override
    public List<Tag> getTagsOfQuestion(Question question) {
        List<Tag> tags = new ArrayList<>();
        for (Question q : repo.keySet()) {
            if (q.equals(question)) {
                tags.add(repo.get(q));
            }
        }
        return tags;
    }

    @Override
    public void remove(Question question, Tag tag) {
        repo.remove(question, tag);
    }
}
