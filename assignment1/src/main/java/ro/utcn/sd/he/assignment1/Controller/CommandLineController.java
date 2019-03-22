package ro.utcn.sd.he.assignment1.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.persistence.api.QuestionTagRepository;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;
import ro.utcn.sd.he.assignment1.service.QuestionService;
import ro.utcn.sd.he.assignment1.service.QuestionTagService;
import ro.utcn.sd.he.assignment1.service.TagService;
import ro.utcn.sd.he.assignment1.service.UserService;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CommandLineController implements CommandLineRunner {
    private final QuestionService questionService;
    private final UserService userService;
    private final TagService tagService;
    private final QuestionTagService questionTagService;
    private User user;
    Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args){
        boolean done = false;
        while(!done){
            print("Enter command:");
            String command = scanner.next().trim();
            done = handleCommand(command);


        }
    }

    private boolean handleCommand(String command){
        switch(command){
            case "search":
                handleSearch();
                return false;
            case "list":
                List<Question> questions = questionService.listQuestions();
                questions.sort(Comparator.comparing(Question::getCreation_date_time).reversed());
                for(Question q : questions){
                    print(q.toString());
                }
                return false;

            case "login":
                if(isLogged()){
                    print("you are already logged in.");
                } else {
                    handleLogin();
                }
                return false;
            case"logout":
                if(isLogged()){
                    user = null;
                    print("Logged out");
                } else {
                    print("You need to be logged in to log out");
                }
                return false;
            case "add":
                handleAdd();
                return false;
            case "delete":
                handleDelete();
                return false;
            case "exit":
                return true;

            case "whoami":
                if(user == null){
                    print("You are not logged in");
                } else {
                    print("Username" + user.getUsername());
                }
                return false;

            default:
                System.out.println("Command unknwon");
                return false;
        }

    }

    private void handleSearch(){
        print("what search criteria you want to use(title/tags):");
        String criteria = scanner.next().trim();
        if(criteria.equals("title")){
            print("Enter title to search:");
            String title1 = scanner.nextLine();///whyyyyyy
            String title = scanner.nextLine();///whyyyyyy
            List<Question> questions = questionService.listQuestions();
            questions.sort(Comparator.comparing(Question::getCreation_date_time).reversed());;
            questions.forEach( question -> {
                if(question.getTitle().indexOf(title) > -1){
                    print(question.toString());
                }
            });
        }else if (criteria.equals("tags")){
            print("enter tag:");
            Integer i = new Integer(0);
            String tagName = scanner.next().trim();
            Tag tag = tagService.getTag(tagName);
            List<Question> questions =  questionTagService.getQuestionsWithTag(tag);
            if(questions.isEmpty()){
                print("No questions found with the given tag");
            }else{
                questions.forEach(question -> print(question.toString()));
            }

        } else {
            print("not known criteria");
        }
    }

    private void handleAdd(){
        if(isLogged()){
            print("enter the title:");
            String title = scanner.nextLine();
            title = scanner.nextLine(); ////whyyyyyyy
            print("enter the text:");
            String text = scanner.nextLine();
            print("Enter some tags:");
            String[] tags = scanner.nextLine().split(" ");
            Question question = questionService.saveQuestion(new Question(0,user.getUsername(),title,text,new Timestamp(System.currentTimeMillis())));
            for(String s: tags){
                Tag tag = tagService.saveTag(new Tag(0, s));
                questionTagService.save(question,tag);
            }
            print("Created question: " + question + ".");
        } else {
            print("You need to be logged in to ask a question");
        }

    }

    private void handleDelete(){
        print("Enter the id of the question you want to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        questionService.deleteQuestion(id);
    }

    private void handleLogin(){
        print("Username:");
        String username = scanner.next().trim();
        print("password:");
        String password = scanner.next().trim();
        user = userService.logIn(username,password);
        if(user == null){
            print("wrond username or password");
        } else{
            print(user.getUsername() + " connected succesfully");
        }

    }

    private boolean isLogged(){
        return user != null;
    }

    private void print(String s){
        System.out.println(s);
    }
}
