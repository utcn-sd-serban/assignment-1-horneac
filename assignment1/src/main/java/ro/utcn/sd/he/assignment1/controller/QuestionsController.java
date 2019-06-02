package ro.utcn.sd.he.assignment1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.he.assignment1.command.AddQuestionCommand;
import ro.utcn.sd.he.assignment1.command.Command;
import ro.utcn.sd.he.assignment1.dto.QuestionDTO;
import ro.utcn.sd.he.assignment1.dto.QuestionVoteDTO;
import ro.utcn.sd.he.assignment1.dto.UserDTO;
import ro.utcn.sd.he.assignment1.dto.VoteDTO;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.service.QuestionService;
import ro.utcn.sd.he.assignment1.service.QuestionTagService;
import ro.utcn.sd.he.assignment1.service.UserService;
import ro.utcn.sd.he.assignment1.service.VoteService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class QuestionsController {
    private final QuestionService questionService;
    private final QuestionTagService questionTagService;
    private final VoteService voteService;
    private final UserService userService;
    private List<Command> commands = new ArrayList<>();

    @GetMapping("questions/{id}")
    public UserDTO readSingle(@PathVariable int id) {
        //TODO
        return null;
    }

    @GetMapping("/questions")
    public List<QuestionVoteDTO> readAll() {
        return questionService.listQuestions();
    }

    @GetMapping("/questions/title/{title}")
    public List<QuestionVoteDTO> searchTitle(@PathVariable String title) {
        return questionService.searchTitle(title);
    }

    @GetMapping("/questions/tag/{tagName}")
    public List<QuestionVoteDTO> searchTag(@PathVariable String tagName) {
        return questionTagService.filterTag(tagName);
    }

    @PostMapping("/questions")
    public QuestionVoteDTO addQuestion(@RequestBody QuestionDTO dto) {
        AddQuestionCommand command = questionTagService.addQuestion(dto);
        commands.add(command);
        QuestionVoteDTO questionDTO = QuestionVoteDTO.ofEntity(command.getQuestion(), voteService.getVoteCount(command.getQuestion()));
        return questionDTO;
    }

    @PostMapping("/questions/vote")
    public VoteDTO vote(@RequestParam(name = "questionId") int questionId, @RequestParam(name = "username") String username, @RequestParam(name = "vote") String vote) {
        int type = vote.equals("up") ? +1 : -1;
        String post = "question";
        User user = userService.findByUsername(username);
        voteService.vote(type, post, questionId, user);
        return new VoteDTO(type);
    }

    @GetMapping("/questions/vote")
    public VoteDTO getVoteOfQuestion(@RequestParam int id) {
        Question question = questionService.findById(id);
        return new VoteDTO(voteService.getVoteCount(question));
    }




}
