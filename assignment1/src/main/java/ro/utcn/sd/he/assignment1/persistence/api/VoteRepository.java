package ro.utcn.sd.he.assignment1.persistence.api;

import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.model.Vote;

import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote);

    void remove(Vote vote);

    List<Vote> getVotesOfUser(User user);

    List<Vote> getVotesOfQuestion(Question question);

    List<Vote> getVotesOfAnswer(Answer answer);

    int getVoteCount(Question question);

    int getVoteCount(Answer answer);

    Vote findVote(User user, Question question);
    Vote findVote(User user, Answer answer);
}
