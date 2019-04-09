package ro.utcn.sd.he.assignment1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.api.QuestionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcQuestionRepository implements QuestionRepository {
    private final JdbcTemplate template;
    private final RowMapper<Question> mapper = ((resultSet, i) -> new Question(resultSet.getInt("id"),
            resultSet.getString("author"),
            resultSet.getString("title"),
            resultSet.getString("text"),
            resultSet.getTimestamp("creation_date_time")));


    @Override
    public Question save(Question question) {
        if (question.getId() != 0) {
            update(question);
        } else {
            int id = insert(question);
            question.setId(id);
        }
        return question;
    }

    @Override
    public Optional<Question> findById(int id) {
        List<Question> questions = template.query("SELECT * FROM question WHERE id = ?",
                new Object[]{id},
                mapper);


        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public void remove(Question question) {
        template.update("DELETE FROM question WHERE id = ?", question.getId());
    }

    @Override
    public List<Question> findAll() {

        return template.query("SELECT * FROM question",
                mapper);

    }

    private int insert(Question question) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question");
        insert.setGeneratedKeyName("id");
        Map<String, Object> data = new HashMap<>();
        data.put("title", question.getTitle());
        data.put("author", question.getAuthor());
        data.put("text", question.getText());
        data.put("creation_date_time", question.getCreation_date_time());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Question question) {
        template.update("UPDATE question SET title = ?, text = ?, author = ?, creation_date_time = ? Where id = ?",
                question.getTitle(), question.getText(), question.getAuthor(), question.getCreation_date_time(), question.getId());
    }
}
