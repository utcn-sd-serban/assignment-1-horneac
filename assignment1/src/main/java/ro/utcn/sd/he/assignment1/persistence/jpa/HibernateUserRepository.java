package ro.utcn.sd.he.assignment1.persistence.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.persistence.api.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateUserRepository implements UserRepository {
    private final EntityManager entityManager;

    @Override
    public User save(User user) {
        if(user.getId() == 0){
            entityManager.persist(user);
            return user;
        }
        else{
            return entityManager.merge(user);
        }
    }

    @Override
    public Optional<User> findByID(int id) {
        return Optional.ofNullable(entityManager.find(User.class,id));
    }

    @Override
    public void remove(User user) {
        entityManager.remove(user);
    }

    @Override
    public User getAuthorOf(Question question) {
        return findByUsername(question.getAuthor()).get();
    }

    @Override
    public User getAuthorOf(Answer answer) {
        return findByUsername(answer.getAuthor()).get();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = criteriaQuery.from(User.class);
        List<User> users = entityManager.createQuery(criteriaQuery.select(userRoot).where(criteriaBuilder.equal(userRoot.get("username"),username))).getResultList();
        return Optional.of(users.size() == 0 ? null : users.get(0));

    }

    @Override
    public List<User> findALL() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        criteriaQuery.select(criteriaQuery.from(User.class));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
