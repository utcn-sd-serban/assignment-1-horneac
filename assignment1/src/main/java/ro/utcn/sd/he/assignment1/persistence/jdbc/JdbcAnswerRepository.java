package ro.utcn.sd.he.assignment1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.api.AnswerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcAnswerRepository implements AnswerRepository {
    private final JdbcTemplate template;
    private RowMapper<Answer> rowMapper = ((resultSet, i) -> new Answer(
            resultSet.getInt("id"),
            resultSet.getString("author"),
            resultSet.getString("text"),
            resultSet.getTimestamp("creation_date_time"),
            resultSet.getInt("questionID")
    ));

    @Override
    public Answer save(Answer answer) {
        if(answer.getId() == 0){
            int id = insert(answer);
            answer.setId(id);
        } else {
            update(answer);
        }
        return answer;
    }

    @Override
    public Optional<Answer> findById(int id) {
        List<Answer> answers = template.query("SELECT * FROM answer WHERE id = ?", rowMapper, id);
        return answers.isEmpty() ? Optional.empty() : Optional.of(answers.get(0));
    }

    @Override
    public List<Answer> getAnswersOf(Question question) {
        List<Answer> answers = template.query("SELECT * FROM question JOIN answer on question.id = answer.questionID WHERE question.id = ?", rowMapper, question.getId());
        return answers;
    }

    @Override
    public void remove(Answer answer) {
        template.update("DELETE FROM answer WHERE id = ?", answer.getId());
    }

    @Override
    public List<Answer> findAll() {
        List<Answer> answers = template.query("SELECT * FROM answer",
                rowMapper);
        return answers;
    }

    private int insert(Answer answer){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("Answer");
        insert.setGeneratedKeyName("id");
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("author",answer.getAuthor());
        data.put("text", answer.getText());
        data.put("creation_date_time", answer.getCreation_date_time());
        data.put("questionID",answer.getQuestionId());
        int id = insert.executeAndReturnKey(data).intValue();
        return id;
    }

    private void update(Answer answer){
        template.update("UPDATE answer SET author = ?, text = ?, creation_date_time = ?, questionID = ? WHERE id = ?",
                answer.getAuthor(),answer.getText(),answer.getCreation_date_time(),answer.getQuestionId(),answer.getId());
    }
}
