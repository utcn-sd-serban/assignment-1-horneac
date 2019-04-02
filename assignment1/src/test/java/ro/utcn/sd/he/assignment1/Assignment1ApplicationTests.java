package ro.utcn.sd.he.assignment1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.persistence.memory.InMemoryRepositoryFactory;
import ro.utcn.sd.he.assignment1.service.QuestionService;
import ro.utcn.sd.he.assignment1.service.UserService;
import ro.utcn.sd.he.assignment1.service.VoteService;

import java.sql.Timestamp;
import java.util.List;

public class Assignment1ApplicationTests {


    private QuestionService questionService = new QuestionService(new InMemoryRepositoryFactory());
    private VoteService voteService = new VoteService(new InMemoryRepositoryFactory());
    private UserService userService = new UserService(new InMemoryRepositoryFactory());

    @Before
    public void programSeed() {
        userService.saveUser(new User(0, "Horneac1", "123456", "user", false, 0));
        userService.saveUser(new User(0, "Horneac2", "123456", "user", false, 0));
        userService.saveUser(new User(0, "Horneac3", "123456", "user", false, 0));
        userService.saveUser(new User(0, "Horneac4", "123456", "user", false, 0));
        questionService.saveQuestion(new Question(0, "author1", "text1", "title1", new Timestamp(System.currentTimeMillis())));
        questionService.saveQuestion(new Question(0, "author2", "text2", "title2", new Timestamp(System.currentTimeMillis())));
        questionService.saveQuestion(new Question(0, "author3", "text3", "title3", new Timestamp(System.currentTimeMillis())));

    }

    @Test
    public void addQuestionTest() {

        Question question = questionService.saveQuestion(new Question(0, "eu", "beautifull", "titlu", new Timestamp(System.currentTimeMillis())));

        Assert.assertEquals(1, questionService.listQuestions().stream().filter(q -> q.getId() == question.getId()).count());
        Assert.assertEquals(4, questionService.listQuestions().size());

        questionService.deleteQuestion(question.getId());
    }

    @Test
    public void voteTestDifferentUsers() {
        List<User> users = userService.listUsers();
        Question question = questionService.findById(1);
        voteService.voteUp(users.get(0), question);
        voteService.voteUp(users.get(1), question);
        voteService.voteUp(users.get(2), question);        //vote up 3 times
        voteService.voteDown(users.get(3), question);    //vote down 1 time

        Assert.assertEquals(2, voteService.getVoteCount(question));    //vote count should be 2
    }

    @Test
    public void voteTestSameUser(){
        User user = userService.listUsers().get(0);
        Question question = questionService.findById(2);
        voteService.voteUp(user,question);
        voteService.voteUp(user,question);
        voteService.voteUp(user,question);
        voteService.voteUp(user,question);
        voteService.voteDown(user,question);

        Assert.assertEquals(-1, voteService.getVoteCount(question));
    }

}
