package ro.utcn.sd.he.assignment1.persistence.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.persistence.api.QuestionRepository;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

@Component
@ConditionalOnProperty(name = "a1.repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory implements RepositoryFactory {
    private final InMemoryQuestionRepository repository = new InMemoryQuestionRepository();
    @Override
    public QuestionRepository createQuestionRepository() {
        return repository;
    }
}
