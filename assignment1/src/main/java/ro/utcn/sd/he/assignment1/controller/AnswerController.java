package ro.utcn.sd.he.assignment1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.he.assignment1.command.AddAnswerCommand;
import ro.utcn.sd.he.assignment1.command.Command;
import ro.utcn.sd.he.assignment1.command.EditAnswerCommand;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.service.AnswerService;
import ro.utcn.sd.he.assignment1.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private List<Command> commandList = new ArrayList<>();

    @GetMapping("/answers")
    public List<Answer> getAnswersOf(@RequestParam int id) {
        Question question = questionService.findById(id);
        return answerService.findAnswersOf(question);
    }

    @PostMapping("/answer")
    public Answer saveAnswer(@RequestBody Answer answer) {
        AddAnswerCommand command = answerService.saveAnswer(answer);
        commandList.add(command);
        return command.getAnswer();
    }

    @PutMapping("/answer")
    public Answer editAnswer(@RequestBody Answer newAnswer) {
        EditAnswerCommand command = answerService.editAnswer(newAnswer);
        commandList.add(command);
        return command.getNewAnswer();
    }

    @DeleteMapping("/answer")
    public void deleteAnswer(@RequestParam int id) {
        Command command = answerService.deleteAnswer(id);
        commandList.add(command);
    }
}
