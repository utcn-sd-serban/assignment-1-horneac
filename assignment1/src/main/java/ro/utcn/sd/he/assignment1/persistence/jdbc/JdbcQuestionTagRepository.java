package ro.utcn.sd.he.assignment1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.persistence.api.QuestionTagRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcQuestionTagRepository implements QuestionTagRepository {
    private final JdbcTemplate template;

    @Override
    public List<Question> getQuestionsWithTag(Tag tag) {
        return template.query("Select * FROM question JOIN question_tag ON question.id = question_tag.questionID" +
                        "                              JOIN tag ON question_tag.tagID = tag.id WHERE tag.id = ?",
                (resultSet, i) -> new Question(
                        resultSet.getInt("id"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getTimestamp("creation_date_time")
                ),
                tag.getId());
    }

    @Override
    public List<Tag> getTagsOfQuestion(Question question) {
        return template.query("Select * FROM question JOIN question_tag ON question.id = question_tag.questionID" +
                        "                              JOIN tag ON question_tag.tagID = tag.id WHERE question.id = ?",
                (resultSet, i) -> new Tag(resultSet.getInt("id"), resultSet.getString("name")),
                question.getId());
    }

    @Override
    public void insert(Question question, Tag tag) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question_tag");
        Map<String, Object> data = new HashMap<>();
        data.put("questionID", question.getId());
        data.put("tagID", tag.getId());
        insert.execute(data);
    }

    @Override
    public void remove(Question question, Tag tag) {
        template.update("DELETE * FROM question_tag WHERE questionID = ?, tagID = ?",
                question.getId(), tag.getId());
    }
}
