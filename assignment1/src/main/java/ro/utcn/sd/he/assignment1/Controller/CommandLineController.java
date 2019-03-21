package ro.utcn.sd.he.assignment1.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;
import ro.utcn.sd.he.assignment1.service.QuestionService;

import java.sql.Timestamp;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CommandLineController implements CommandLineRunner {
    private final QuestionService questionService;
    Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) throws Exception {
        boolean done = false;
        while(!done){
            print("Enter command:");
            String command = scanner.next().trim();
            done = handleCommand(command);


        }
    }

    private boolean handleCommand(String command){
        switch(command){
            case "list":
                questionService.listQuestions().forEach(s ->print(s.toString()));
                return false;

            case "add":
                handleAdd();
                return false;
            case "delete":
                handleDelete();
                return false;
            case "exit":
                return true;

            default:
                System.out.println("Command unknwon");
                return false;
        }

    }

    private void handleAdd(){
        print("enter the title:");
        String title = scanner.nextLine();
        title = scanner.nextLine(); ////whyyyyyyy
        print("enter the text:");
        String text = scanner.nextLine();
        Question question = questionService.saveQuestion(new Question(0,"CurrentUser",title,text,new Timestamp(System.currentTimeMillis())));
        print("Created question: " + question + ".");
    }

    private void handleDelete(){
        print("Enter the id of the question you want to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        questionService.deleteQuestion(id);
    }

    private void print(String s){
        System.out.println(s);
    }
}
