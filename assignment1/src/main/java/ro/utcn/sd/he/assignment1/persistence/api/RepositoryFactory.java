package ro.utcn.sd.he.assignment1.persistence.api;

@FunctionalInterface
public interface RepositoryFactory {

    QuestionRepository createQuestionRepository();


}
