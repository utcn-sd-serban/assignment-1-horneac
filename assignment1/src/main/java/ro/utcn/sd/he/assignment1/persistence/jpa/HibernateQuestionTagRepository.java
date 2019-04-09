package ro.utcn.sd.he.assignment1.persistence.jpa;

import lombok.RequiredArgsConstructor;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.QuestionTag;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.persistence.api.QuestionTagRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HibernateQuestionTagRepository implements QuestionTagRepository {
    private final EntityManager entityManager;

    @Override
    public void insert(Question question, Tag tag) {
        QuestionTag questionTag = new QuestionTag(question.getId(), tag.getId());
        entityManager.persist(questionTag);
    }

    @Override
    public void remove(Question question, Tag tag) {
        QuestionTag questionTag = new QuestionTag(question.getId(), tag.getId());
        entityManager.remove(questionTag);
    }

    @Override
    public List<Question> getQuestionsWithTag(Tag tag) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(QuestionTag.class);
        Root<QuestionTag> questionRoot = criteriaQuery.from(QuestionTag.class);
        List<QuestionTag> questionTags = entityManager.createQuery(criteriaQuery.select(questionRoot).where(criteriaBuilder.equal(questionRoot.get("tagID"), tag.getId()))).getResultList();
        //since i did not implement the many to many relationship i have to do this trick where I get this ^ list which basically contains all the question Ids that i need
        HibernateQuestionRepository questionRepository = new HibernateQuestionRepository(entityManager);
        List<Question> questions = new ArrayList<>();
        for (QuestionTag questionTag : questionTags) {
            questions.add(questionRepository.findById(questionTag.getQuestionID()).get());
        }
        return questions;
    }

    @Override
    public List<Tag> getTagsOfQuestion(Question question) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(QuestionTag.class);
        Root<QuestionTag> questionRoot = criteriaQuery.from(QuestionTag.class);
        List<QuestionTag> questionTags = entityManager.createQuery(criteriaQuery.select(questionRoot).where(criteriaBuilder.equal(questionRoot.get("questionID"), question.getId()))).getResultList();
        //since i did not implement the many to many relationship i have to do this trick where I get this ^ list which basically contains all the question Ids that i need
        HibernateTagRepository tagRepository = new HibernateTagRepository(entityManager);
        List<Tag> tags = new ArrayList<>();
        for (QuestionTag questionTag : questionTags) {
            tags.add(tagRepository.findById(questionTag.getTagID()).get());
        }
        return tags;
    }

    @Transactional
    public List<Tag> findTagsByIdsIn(List<Integer> ids) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tagRoot = query.from(Tag.class);
        query.select(tagRoot).where(criteriaBuilder.and(tagRoot.get("id").in(ids)));

        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    public List<Question> findQuestionsByIdsIn(List<Integer> ids) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(Question.class);
        Root<Question> questionRoot = query.from(Question.class);
        query.select(questionRoot).where(criteriaBuilder.and(questionRoot.get("id").in(ids)));

        return entityManager.createQuery(query).getResultList();
    }

}
