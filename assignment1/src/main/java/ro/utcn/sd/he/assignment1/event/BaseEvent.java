package ro.utcn.sd.he.assignment1.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BaseEvent {
    private final EventType type;

}
