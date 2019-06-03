package ro.utcn.sd.he.assignment1.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.he.assignment1.dto.AnswerVoteDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerVoteEvent extends BaseEvent {
    private final AnswerVoteDTO answer;

    public AnswerVoteEvent(AnswerVoteDTO answer) {
        super(EventType.ANSWER_VOTE);
        this.answer = answer;
    }
}
