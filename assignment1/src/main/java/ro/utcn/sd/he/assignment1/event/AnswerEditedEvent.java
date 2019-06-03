package ro.utcn.sd.he.assignment1.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.he.assignment1.dto.AnswerVoteDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerEditedEvent extends BaseEvent {
    private final AnswerVoteDTO answer;

    public AnswerEditedEvent(AnswerVoteDTO answer) {
        super(EventType.ANSWER_EDITED);
        this.answer = answer;
    }
}
