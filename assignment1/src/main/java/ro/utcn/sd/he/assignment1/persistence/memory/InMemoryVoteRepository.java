package ro.utcn.sd.he.assignment1.persistence.memory;

import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.model.Vote;
import ro.utcn.sd.he.assignment1.persistence.api.VoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryVoteRepository implements VoteRepository {
    private final Map<Integer, Vote> repo = new ConcurrentHashMap<>();
    private AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public Vote findVote(User user, Question question) {
        for(Vote vote : repo.values()){
            if(vote.getQuestionID() == question.getId() & vote.getUserID() == user.getId()){
                return vote;
            }
        }
        return null;
    }

    @Override
    public Vote findVote(User user, Answer answer) {
        for(Vote vote : repo.values()){
            if(vote.getAnswerID() == answer.getId() & vote.getUserID() == user.getId()){
                return vote;
            }
        }
        return null;
    }

    @Override
    public Vote save(Vote vote) {
        if (vote.getId() == 0) {
            vote.setId(currentId.getAndIncrement());
            repo.put(vote.getId(), vote);
        } else {
            repo.put(vote.getId(), vote);
        }
        return vote;
    }

    @Override
    public void remove(Vote vote) {
        repo.remove(vote.getId());
    }

    @Override
    public List<Vote> getVotesOfUser(User user) {
        List<Vote> votes = new ArrayList<>();
        for (Vote vote : repo.values()) {
            if (vote.getUserID() == user.getId()) {
                votes.add(vote);
            }
        }
        return votes;
    }

    @Override
    public List<Vote> getVotesOfQuestion(Question question) {
        List<Vote> votes = new ArrayList<>();
        for (Vote vote : repo.values()) {
            if (vote.getQuestionID() == question.getId()) {
                votes.add(vote);
            }
        }
        return votes;
    }

    @Override
    public List<Vote> getVotesOfAnswer(Answer answer) {
        List<Vote> votes = new ArrayList<>();
        for (Vote vote : repo.values()) {
            if (vote.getAnswerID() == answer.getId()) {
                votes.add(vote);
            }
        }
        return votes;
    }

    @Override
    public int getVoteCount(Question question) {
        int voteCount = 0;
        for (Vote vote : repo.values()) {
            if (vote.getQuestionID() == question.getId()) {
                voteCount += vote.getType();
            }
        }
        return voteCount;
    }

    @Override
    public int getVoteCount(Answer answer) {
        int voteCount = 0;
        for (Vote vote : repo.values()) {
            if (vote.getAnswerID() == answer.getId()) {
                voteCount += vote.getType();
            }
        }
        return voteCount;
    }
}
