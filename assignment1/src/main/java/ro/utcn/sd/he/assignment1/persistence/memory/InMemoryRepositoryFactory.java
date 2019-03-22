package ro.utcn.sd.he.assignment1.persistence.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.persistence.api.*;

@Component
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory implements RepositoryFactory {
    private final InMemoryQuestionRepository repository = new InMemoryQuestionRepository();
    private final InMemoryUserRepository userRepo = new InMemoryUserRepository();
    private final InMemoryTagRepository tagRepo = new InMemoryTagRepository();
    private final InMemoryQuestionTagRepository questionTagRepo = new InMemoryQuestionTagRepository();
    @Override
    public QuestionRepository createQuestionRepository() {
        return repository;
    }

    @Override
    public QuestionTagRepository createQuestionTagRepository() {
        return questionTagRepo;
    }

    @Override
    public TagRepository createTagRepository() {
        return tagRepo;
    }

    @Override
    public UserRepository createUserRepository() {
        return userRepo;
    }
}
