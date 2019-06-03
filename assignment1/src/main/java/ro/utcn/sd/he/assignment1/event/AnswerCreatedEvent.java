package ro.utcn.sd.he.assignment1.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.he.assignment1.dto.AnswerVoteDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerCreatedEvent extends BaseEvent {
    private final AnswerVoteDTO answer;

    public AnswerCreatedEvent(AnswerVoteDTO answer) {
        super(EventType.ANSWER_CREATED);
        this.answer = answer;
    }
}
