package ro.utcn.sd.he.assignment1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.model.Vote;
import ro.utcn.sd.he.assignment1.persistence.api.VoteRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JdbcVoteRepository implements VoteRepository {
    private final JdbcTemplate template;
    private RowMapper<Vote> rowMapper = ((resultSet, i) -> new Vote(
            resultSet.getInt("id"),
            resultSet.getInt("type"),
            resultSet.getInt("userID"),
            resultSet.getInt("questionID"),
            resultSet.getInt("answerID")
    ));

    @Override
    public Vote findVote(User user, Question question) {
       List<Vote> votes = template.query("SELECT * FROM vote WHERE questionID = ? and userID = ?",rowMapper,question.getId(),user.getId());
        if(votes.size() == 0){
            return null;
        }else {
            return votes.get(0);
        }

    }

    @Override
    public Vote findVote(User user, Answer answer) {
        List<Vote> votes =  template.query("SELECT * FROM vote WHERE answerID = ? and userID = ?",rowMapper,answer.getId(),user.getId());
        if(votes.size() == 0){
            return null;
        } else {
            return votes.get(0);
        }
    }

    @Override
    public Vote save(Vote vote) {
        if (vote.getId() == 0) {
            int id = insert(vote);
            vote.setId(id);
        } else {
            update(vote);
        }
        return vote;
    }

    @Override
    public void remove(Vote vote) {
        template.update("DELETE FROM vote WHERE id = ?", vote.getId());
    }

    @Override
    public List<Vote> getVotesOfUser(User user) {
        List<Vote> votes = template.query("SELECT * FROM vote JOIN user on vote.userID = user.id where user.id = ?", rowMapper, user.getId());
        return votes;
    }

    @Override
    public List<Vote> getVotesOfQuestion(Question question) {
        List<Vote> votes = template.query("SELECT * FROM vote WHERE questionID = ?", rowMapper, question.getId());
        return votes;
    }

    @Override
    public List<Vote> getVotesOfAnswer(Answer answer) {
        List<Vote> votes = template.query("SELECT * FROM vote WHERE answerID = ?", rowMapper, answer.getId());
        return votes;
    }

    public int getVoteCount(Question question) {
        int voteCount = template.query("SELECT sum(type) as votecount FROM vote WHERE questionID = ?", (resultSet, i) -> resultSet.getInt(("votecount")), question.getId()).get(0);
        return voteCount;
    }

    public int getVoteCount(Answer answer) {
        int voteCount = template.query("SELECT sum(type) as votecount FROM vote WHERE answerID = ?", (resultSet, i) -> resultSet.getInt(("votecount")), answer.getId()).get(0);
        return voteCount;
    }


    private int insert(Vote vote) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("vote");
        insert.setGeneratedKeyName("id");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("type", vote.getType());
        data.put("userID", vote.getUserID());
        data.put("questionID", vote.getQuestionID() == 0 ? null : vote.getQuestionID());
        data.put("answerID", vote.getAnswerID() == 0 ? null : vote.getAnswerID());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Vote vote) {
        template.update("UPDATE vote set type = ?, userID = ?, questionID = ?, answerID = ? WHERE id = ?",
                vote.getType(), vote.getUserID(), vote.getQuestionID() == 0? null : vote.getQuestionID(), vote.getAnswerID() == 0 ? null : vote.getAnswerID(), vote.getId());
    }
}
