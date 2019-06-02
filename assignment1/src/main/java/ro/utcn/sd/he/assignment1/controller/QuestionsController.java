package ro.utcn.sd.he.assignment1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.he.assignment1.command.AddQuestionCommand;
import ro.utcn.sd.he.assignment1.command.Command;
import ro.utcn.sd.he.assignment1.dto.QuestionDTO;
import ro.utcn.sd.he.assignment1.dto.UserDTO;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.service.QuestionService;
import ro.utcn.sd.he.assignment1.service.QuestionTagService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class QuestionsController {
    private final QuestionService questionService;
    private final QuestionTagService questionTagService;
    private List<Command> commands = new ArrayList<>();

    @GetMapping("questions/{id}")
    public UserDTO readSingle(@PathVariable int id) {
        //TODO
        return null;
    }

    @GetMapping("/questions")
    public List<Question> readAll() {
        return questionService.listQuestions();
    }

    @GetMapping("/questions/title/{title}")
    public List<Question> searchTitle(@PathVariable String title) {
        return questionService.searchTitle(title);
    }

    @GetMapping("/questions/tag/{tagName}")
    public List<Question> searchTag(@PathVariable String tagName) {
        return questionTagService.filterTag(tagName);
    }

    @PostMapping("/questions")
    public QuestionDTO addQuestion(@RequestBody QuestionDTO dto) {
        AddQuestionCommand command = questionTagService.addQuestion(dto);
        commands.add(command);
        QuestionDTO questionDTO = QuestionDTO.ofEntity(command.getQuestion());
        return questionDTO;
    }

//    @PostMapping("/users")
//    public UserDTO create(@RequestBody UserDTO dto) {
//        return userService.saveUser(dto);
//    }

}
