package ro.utcn.sd.he.assignment1.persistence.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.api.AnswerRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateAnswerRepository implements AnswerRepository {
    private final EntityManager entityManager;

    @Override
    public Answer save(Answer answer) {
        if (answer.getId() == 0) {
            entityManager.persist(answer);
            return answer;
        } else {
            return entityManager.merge(answer);
        }
    }

    @Override
    public Optional<Answer> findById(int id) {
        return Optional.ofNullable(entityManager.find(Answer.class, id));
    }

    @Override
    public List<Answer> getAnswersOf(Question question) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Answer.class);
        Root<Answer> answerRoot = criteriaQuery.from(Answer.class);
        return entityManager.createQuery(criteriaQuery.select(answerRoot).where(criteriaBuilder.equal(answerRoot.get("questionID"),question.getId()))).getResultList();
    }

    @Override
    public void remove(Answer answer) {

        entityManager.remove(answer);
    }

    @Override
    public List<Answer> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Answer> criteriaQuery = criteriaBuilder.createQuery(Answer.class);
        criteriaQuery.select(criteriaQuery.from(Answer.class));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
