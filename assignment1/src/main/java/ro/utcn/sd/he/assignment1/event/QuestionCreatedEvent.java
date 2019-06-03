package ro.utcn.sd.he.assignment1.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ro.utcn.sd.he.assignment1.dto.QuestionVoteDTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionCreatedEvent extends BaseEvent {
    private final QuestionVoteDTO question;

    public QuestionCreatedEvent(QuestionVoteDTO question) {
        super(EventType.QUESTION_CREATED);
        this.question = question;
    }
}
