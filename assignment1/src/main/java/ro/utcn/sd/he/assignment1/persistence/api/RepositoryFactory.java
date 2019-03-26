package ro.utcn.sd.he.assignment1.persistence.api;


public interface RepositoryFactory {

    QuestionRepository createQuestionRepository();

    UserRepository createUserRepository();

    TagRepository createTagRepository();

    QuestionTagRepository createQuestionTagRepository();

    AnswerRepository createAnswerRepository();


}
