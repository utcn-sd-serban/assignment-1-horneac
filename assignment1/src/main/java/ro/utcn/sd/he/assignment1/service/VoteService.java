package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.model.Vote;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final RepositoryFactory factory;
    private final AnswerService answerService;
    private final QuestionService questionService;

    @Transactional
    public Vote save(Vote vote) {
        return factory.createVoteRepository().save(vote);
    }

    @Transactional
    public void remove(Vote vote) {
        factory.createVoteRepository().remove(vote);
    }

    @Transactional
    public List<Vote> getVotesOfUser(User user) {
        return factory.createVoteRepository().getVotesOfUser(user);
    }

    @Transactional
    public List<Vote> getVotesOfQuestion(Question question) {
        return factory.createVoteRepository().getVotesOfQuestion(question);
    }

    @Transactional
    public List<Vote> getVotesOfAnswer(Answer answer) {
        return factory.createVoteRepository().getVotesOfAnswer(answer);
    }

    @Transactional
    public boolean vote(int type, String post, int postId, User currentUser) {
        if (post.equals("question")) {
            Question question = questionService.findById(postId);
            if (type != +1) {
                if (type == -1) {
                    voteDown(currentUser, question);
                } else {
                    return false;
                }
            } else {
                voteUp(currentUser, question);
            }
        } else if (post.equals("answer")) {
            Answer answer = answerService.findById(postId);
            if (type == +1) {
                voteUp(currentUser, answer);
            } else if (type == -1) {
                voteDown(currentUser, answer);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Transactional
    public int getVoteCount(Question question) {
        return factory.createVoteRepository().getVoteCount(question);
    }

    @Transactional
    public int getVoteCount(Answer answer) {
        return factory.createVoteRepository().getVoteCount(answer);
    }

    @Transactional
    public Vote voteUp(User user, Question question) {
        Vote vote = factory.createVoteRepository().findVote(user, question);
        if (factory.createQuestionRepository().findById(question.getId()).isPresent() &
                factory.createUserRepository().findByUsername(user.getUsername()).isPresent()) {

            if (vote != null) {
                vote.setType(1);
                vote = save(vote);
            } else {
                vote = new Vote(0, 1, user.getId(), question.getId(), null);
                vote = save(vote);
            }
            return vote;
        } else {
            return vote;
        }
    }


    @Transactional
    public Vote voteDown(User user, Question question) {
        Vote vote = factory.createVoteRepository().findVote(user, question);
        if (factory.createQuestionRepository().findById(question.getId()).isPresent() &
                factory.createUserRepository().findByUsername(user.getUsername()).isPresent()) {

            if (vote != null) {
                vote.setType(-1);
                vote = save(vote);
            } else {
                vote = new Vote(0, -1, user.getId(), question.getId(), null);
                vote = save(vote);
            }
        }
        return vote;

    }

    @Transactional
    public Vote voteUp(User user, Answer answer) {
        Vote vote = factory.createVoteRepository().findVote(user, answer);
        if (factory.createQuestionRepository().findById(answer.getId()).isPresent() &
                factory.createUserRepository().findByUsername(user.getUsername()).isPresent()) {

            if (vote != null) {
                vote.setType(1);
                vote = save(vote);
            } else {
                vote = new Vote(0, 1, user.getId(), null, answer.getId());
                vote = save(vote);
            }
        }
        return vote;
    }

    @Transactional
    public Vote voteDown(User user, Answer answer) {
        Vote vote = factory.createVoteRepository().findVote(user, answer);
        if (factory.createQuestionRepository().findById(answer.getId()).isPresent() &
                factory.createUserRepository().findByUsername(user.getUsername()).isPresent()) {
            if (vote != null) {
                vote.setType(-1);
                vote = save(vote);
            } else {
                vote = new Vote(0, -1, user.getId(), null, answer.getId());
                vote = save(vote);
            }
        }
        return vote;
    }

}
