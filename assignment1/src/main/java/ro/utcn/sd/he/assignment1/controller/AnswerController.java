package ro.utcn.sd.he.assignment1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.he.assignment1.command.AddAnswerCommand;
import ro.utcn.sd.he.assignment1.command.Command;
import ro.utcn.sd.he.assignment1.command.EditAnswerCommand;
import ro.utcn.sd.he.assignment1.dto.AnswerVoteDTO;
import ro.utcn.sd.he.assignment1.dto.VoteDTO;
import ro.utcn.sd.he.assignment1.event.*;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.service.AnswerService;
import ro.utcn.sd.he.assignment1.service.QuestionService;
import ro.utcn.sd.he.assignment1.service.UserService;
import ro.utcn.sd.he.assignment1.service.VoteService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AnswerController {
    private final AnswerService answerService;
    private final UserService userService;
    private final QuestionService questionService;
    private final VoteService voteService;

    private final SimpMessagingTemplate messagingTemplate;
    private List<Command> commandList = new ArrayList<>();

    @GetMapping("/answers")
    public List<AnswerVoteDTO> getAnswersOf(@RequestParam int id) {
        Question question = questionService.findById(id);
        return answerService.findAnswersOf(question);
    }

    @PostMapping("/answer")
    public AnswerVoteDTO saveAnswer(@RequestBody Answer answer) {
        AddAnswerCommand command = answerService.saveAnswer(answer);
        commandList.add(command);
        AnswerVoteDTO output = AnswerVoteDTO.ofEntity(command.getAnswer(), voteService.getVoteCount(command.getAnswer()));

        BaseEvent event = new AnswerCreatedEvent(output);
        messagingTemplate.convertAndSend("/topic/events", event);
        log.info("Got an event: {}.", event);

        return output;
    }

    @PutMapping("/answer")
    public AnswerVoteDTO editAnswer(@RequestBody Answer newAnswer) {
        EditAnswerCommand command = answerService.editAnswer(newAnswer);
        commandList.add(command);
        AnswerVoteDTO output = AnswerVoteDTO.ofEntity(command.getNewAnswer(), voteService.getVoteCount(command.getNewAnswer()));

        BaseEvent event = new AnswerEditedEvent(output);
        messagingTemplate.convertAndSend("/topic/events", event);
        log.info("Got an event: {}.", event);

        return output;
    }

    @DeleteMapping("/answer")
    public void deleteAnswer(@RequestParam int id) {
        Command command = answerService.deleteAnswer(id);
        commandList.add(command);

        BaseEvent event = new AnswerDeletedEvent(id);
        messagingTemplate.convertAndSend("/topic/events", event);
        log.info("Got an event: {}.", event);
    }

    @PostMapping("/answers/vote")
    public VoteDTO vote(@RequestParam(name = "answerId") int answerId, @RequestParam(name = "username") String username, @RequestParam(name = "vote") String vote) {
        int type = vote.equals("up") ? +1 : -1;
        String post = "answer";
        User user = userService.findByUsername(username);
        voteService.vote(type, post, answerId, user);

        Answer answer = answerService.findById(answerId);
        int voteCount = voteService.getVoteCount(answer);
        BaseEvent event = new AnswerVoteEvent(AnswerVoteDTO.ofEntity(answer, voteCount));
        messagingTemplate.convertAndSend("/topic/events", event);
        log.info("Got an event: {}.", event);

        return new VoteDTO(type);
    }

    @GetMapping("/answers/vote")
    public VoteDTO getVoteOfAnswer(@RequestParam int id) {
        Answer answer = answerService.findById(id);
        return new VoteDTO(voteService.getVoteCount(answer));
    }

}
