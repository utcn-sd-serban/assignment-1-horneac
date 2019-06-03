package ro.utcn.sd.he.assignment1.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AnswerDeletedEvent extends BaseEvent {
    private final int id;

    public AnswerDeletedEvent(int id) {
        super(EventType.ANSWER_DELETED);
        this.id = id;
    }
}
