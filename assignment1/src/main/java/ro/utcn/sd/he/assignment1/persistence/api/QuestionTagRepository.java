package ro.utcn.sd.he.assignment1.persistence.api;

import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.Tag;

import java.util.List;

public interface QuestionTagRepository {

    void insert(Question question, Tag tag);

    void remove(Question question, Tag tag);
    List<Question> getQuestionsWithTag(Tag tag);

    List<Tag> getTagsOfQuestion(Question question);
}
