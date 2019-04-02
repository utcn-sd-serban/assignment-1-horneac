package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.model.Vote;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final RepositoryFactory factory;

    public Vote save(Vote vote){
        return factory.createVoteRepository().save(vote);
    }

    public void remove(Vote vote){
        factory.createVoteRepository().remove(vote);
    }

    public List<Vote> getVotesOfUser(User user){
        return factory.createVoteRepository().getVotesOfUser(user);
    }

    public List<Vote> getVotesOfQuestion(Question question){
        return factory.createVoteRepository().getVotesOfQuestion(question);
    }

    public List<Vote> getVotesOfAnswer(Answer answer){
        return factory.createVoteRepository().getVotesOfAnswer(answer);
    }

    public int getVoteCount(Question question){
        return factory.createVoteRepository().getVoteCount(question);
    }

    public int getVoteCount(Answer answer){
        return factory.createVoteRepository().getVoteCount(answer);
    }

    public Vote voteUp(User user, Question question){
       Vote vote = factory.createVoteRepository().findVote(user,question);
       if(vote != null){
           vote.setType(1);
           vote = save(vote);
       } else {
           vote = new Vote(0,1,user.getId(),question.getId(),0);
            vote = save(vote);
       }
        return vote;
    }

    public Vote voteDown(User user, Question question){
        Vote vote = factory.createVoteRepository().findVote(user,question);
        if(vote != null){
            vote.setType(-1);
            vote = save(vote);
        } else {
            vote = new Vote(0,-1,user.getId(),question.getId(),0);
            vote = save(vote);
        }
        return vote;
    }


    public Vote voteUp(User user, Answer answer){
        Vote vote = factory.createVoteRepository().findVote(user,answer);
        if(vote != null){
            vote.setType(1);
            vote = save(vote);
        } else {
            vote = new Vote(0,1,user.getId(),0,answer.getId());
            vote = save(vote);
        }
        return vote;
    }

    public Vote voteDown(User user, Answer answer){
        Vote vote = factory.createVoteRepository().findVote(user,answer);
        if(vote != null){
            vote.setType(-1);
            vote = save(vote);
        } else {
            vote = new Vote(0,-1,user.getId(), 0,answer.getId());
            vote = save(vote);
        }
        return vote;
    }

}
