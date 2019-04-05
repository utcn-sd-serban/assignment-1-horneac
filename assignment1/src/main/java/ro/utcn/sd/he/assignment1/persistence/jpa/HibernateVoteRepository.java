package ro.utcn.sd.he.assignment1.persistence.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.model.Vote;
import ro.utcn.sd.he.assignment1.persistence.api.VoteRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@RequiredArgsConstructor
public class HibernateVoteRepository implements VoteRepository {
    private final EntityManager entityManager;

    @Override
    public Vote save(Vote vote) {
        if (vote.getId() == 0) {
            entityManager.persist(vote);
            return vote;
        } else {
            return entityManager.merge(vote);
        }
    }

    @Override
    public void remove(Vote vote) {
        entityManager.remove(vote);
    }

    @Override
    public List<Vote> getVotesOfUser(User user) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Vote.class);
        Root<Vote> voteRoot = criteriaQuery.from(Vote.class);
        List<Vote> votes = entityManager.createQuery(criteriaQuery.select(voteRoot).where(criteriaBuilder.equal(voteRoot.get("userID"), user.getId()))).getResultList();
        return votes;
    }

    @Override
    public List<Vote> getVotesOfQuestion(Question question) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Vote.class);
        Root<Vote> voteRoot = criteriaQuery.from(Vote.class);
        List<Vote> votes = entityManager.createQuery(criteriaQuery.select(voteRoot).where(criteriaBuilder.equal(voteRoot.get("questionID"), question.getId()))).getResultList();
        return votes;
    }

    @Override
    public List<Vote> getVotesOfAnswer(Answer answer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Vote.class);
        Root<Vote> voteRoot = criteriaQuery.from(Vote.class);
        List<Vote> votes = entityManager.createQuery(criteriaQuery.select(voteRoot).where(criteriaBuilder.equal(voteRoot.get("answerID"), answer.getId()))).getResultList();
        return votes;
    }

    @Override
    public int getVoteCount(Question question) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Vote.class);

        Root<Vote> voteRoot = criteriaQuery.from(Vote.class);
        criteriaQuery.select(criteriaBuilder.sum(voteRoot.get("type"))).where(criteriaBuilder.equal(voteRoot.get("questionID"), question.getId()));
        Object result = entityManager.createQuery(criteriaQuery).getSingleResult();
        int votes = result == null? 0 : (int) result;
        return votes;
    }

    @Override
    public int getVoteCount(Answer answer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Vote.class);

        Root<Vote> voteRoot = criteriaQuery.from(Vote.class);
        criteriaQuery.select(criteriaBuilder.sum(voteRoot.get("type"))).where(criteriaBuilder.equal(voteRoot.get("answerID"), answer.getId()));
        Object result = entityManager.createQuery(criteriaQuery).getSingleResult();
        int votes = result == null ? 0 : (int) result;
        return votes;
    }

    @Override
    public Vote findVote(User user, Question question) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Vote.class);
        Root<Vote> voteRoot = criteriaQuery.from(Vote.class);
        List<Vote> votes = entityManager.createQuery(criteriaQuery.select(voteRoot).where(criteriaBuilder.and(criteriaBuilder.equal(voteRoot.get("userID"), user.getId()), criteriaBuilder.equal(voteRoot.get("questionID"), question.getId())))).getResultList();
        return votes.size() == 0 ? null : votes.get(0);
    }

    @Override
    public Vote findVote(User user, Answer answer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Vote.class);
        Root<Vote> voteRoot = criteriaQuery.from(Vote.class);
        List<Vote> votes = entityManager.createQuery(criteriaQuery.select(voteRoot).where(criteriaBuilder.and(criteriaBuilder.equal(voteRoot.get("userID"), user.getId()), criteriaBuilder.equal(voteRoot.get("answerID"), answer.getId())))).getResultList();
        return votes.size() == 0 ? null : votes.get(0);
    }
}
