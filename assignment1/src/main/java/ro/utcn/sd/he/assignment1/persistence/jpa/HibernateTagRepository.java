package ro.utcn.sd.he.assignment1.persistence.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.persistence.api.TagRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HibernateTagRepository implements TagRepository {
    private final EntityManager entityManager;

    @Override
    public Tag save(Tag tag) {
        if(tag.getId() == 0){
            entityManager.persist(tag);
            return tag;
        }
        else{
            return entityManager.merge(tag);
        }
    }

    @Override
    public Optional<Tag> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = criteriaQuery.from(Tag.class);
        List<Tag> tags = entityManager.createQuery(criteriaQuery.select(tagRoot).where(criteriaBuilder.equal(tagRoot.get("name"),name))).getResultList();
        return Optional.of(tags.get(0));
    }

    @Override
    public List<Tag> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        criteriaQuery.select(criteriaQuery.from(Tag.class));
        return entityManager.createQuery(criteriaQuery).getResultList();

    }

    public Optional<Tag> findById(int id){
        return Optional.ofNullable(entityManager.find(Tag.class,id));
    }
}
