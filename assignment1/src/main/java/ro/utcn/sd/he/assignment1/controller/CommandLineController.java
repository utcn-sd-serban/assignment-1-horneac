package ro.utcn.sd.he.assignment1.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.service.*;

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
    private final AnswerService answerService;
    private final VoteService voteService;
    private User user;
    Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args){
        boolean done = false;
        while(!done){
            print("Enter command:");
            String command = scanner.next().trim();
            scanner.nextLine();     //dump the line
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

            case"vote":
                handleVote();
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

            case "listAnswers":
                handleListAnswers();
                return false;

            case "deleteAnswer":
                handleDeleteAnswer();
                return false;

            case "edit":
                handleEdit();
                return false;

            case "show":
                handleShow();
                return false;

            case "answer":
                handleAnswer();
                return false;
            default:
                System.out.println("Command unknwon");
                return false;
        }

    }

    private void handleListAnswers(){
        answerService.findAllAnswers().forEach( a -> print(a.toString()));
    }

    private void handleEdit(){
        if(isLogged()){
            print("What answer do you want to edit (id):");
            int answerId = scanner.nextInt();
            scanner.nextLine(); //dump remaining line
            Answer answer = answerService.findById(answerId);
            if(userService.getAuthorOf(answer).getUsername().equals(user.getUsername())){
                print("New text:");
                String text = scanner.nextLine();
                answer.setText(text);           //TODO when it is edited, should i change the creation time? or add last edited time?
                answerService.saveAnswer(answer);
            } else {
                print("You can edit only your own questions!");
            }
        } else {
            print("You need to be logged in to edit an answer!");
        }
    }

    public void handleDeleteAnswer(){
        print("What answer(id) you want to delete:");
        int id = scanner.nextInt();
        scanner.nextLine();
        answerService.deleteAnswer(id);
    }

    public void handleVote(){
        if(user == null){
            print("you need to be logged in to vote!");
            return;
        }
        print("question or answer?");
        String type = scanner.next().trim();
        scanner.nextLine(); //drop remaining line
        print("insert the id of the post you want to vote:");
        int id = scanner.nextInt();
        scanner.nextLine();
        print("+1 or -1?");
        int vote = scanner.nextInt();
        scanner.nextLine();
        if(type.equals("question")){
            Question question = questionService.findById(id);
            if(vote == +1){
                voteService.voteUp(user, question);
            }
            else if(vote == -1){
                voteService.voteDown(user,question);
            } else {
                print("invalid vote option");
            }
        } else if ( type.equals("answer")){
            Answer answer = answerService.findById(id);
            if(vote == +1){
                voteService.voteUp(user, answer);
            }
            else if(vote == -1){
                voteService.voteDown(user,answer);
            } else {
                print("invalid vote option");
            }
        } else {
            print("You can only vote a question or an answer");
        }
    }

    private void handleShow(){
        print("Select question(id):");
        int questionId = scanner.nextInt();
        Question question = questionService.findById(questionId);
        List<Answer> answers = answerService.findAnswersOf(question);
        print(question.toString() + "Votes: " + voteService.getVoteCount(question));
        for(Answer a : answers){
            print("\t" + a.toString() + "Votes: " + voteService.getVoteCount(a));
        }

    }

    private void handleAnswer(){
        if (isLogged()){
            print("what question(id) do you want to answer:");
            int questionId = scanner.nextInt();
            scanner.nextLine(); //dump line
            print("give your answer:");
            String text = scanner.nextLine();
            answerService.saveAnswer(new Answer(0,user.getUsername(),text, new Timestamp(System.currentTimeMillis()),questionId));

        } else {
            print("You need to login to answer a question");
        }
    }

    private void handleSearch(){
        print("what search criteria you want to use(title/tags):");
        String criteria = scanner.next().trim();
        scanner.nextLine();
        if(criteria.equals("title")){
            print("Enter title to search:");
            String title = scanner.nextLine();
            List<Question> questions = questionService.listQuestions();
            questions.sort(Comparator.comparing(Question::getCreation_date_time).reversed());;
            questions.forEach( question -> {
                if(question.getTitle().toLowerCase().indexOf(title.toLowerCase()) > -1){
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
