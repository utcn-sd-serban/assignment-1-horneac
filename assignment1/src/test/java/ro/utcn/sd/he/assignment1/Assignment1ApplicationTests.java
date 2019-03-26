package ro.utcn.sd.he.assignment1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.memory.InMemoryRepositoryFactory;
import ro.utcn.sd.he.assignment1.service.*;

import java.sql.Timestamp;

public class Assignment1ApplicationTests {


	private QuestionService questionService = new QuestionService(new InMemoryRepositoryFactory());

	@Before
	public void programSeed() {
		questionService.saveQuestion(new Question(0,"author1","text1","title1",new Timestamp(System.currentTimeMillis())));
		questionService.saveQuestion(new Question(0,"author2","text2","title2",new Timestamp(System.currentTimeMillis())));
		questionService.saveQuestion(new Question(0,"author3","text3","title3",new Timestamp(System.currentTimeMillis())));

	}

	@Test
	public void addQuestionTest()
	{

		Question question = questionService.saveQuestion(new Question(0,"eu","beautifull","titlu",new Timestamp(System.currentTimeMillis())));

		Assert.assertEquals(1, questionService.listQuestions().stream().filter( q -> q.getId() == question.getId()).count());
		Assert.assertEquals(4,questionService.listQuestions().size());

		questionService.deleteQuestion(question.getId());
	}

}
