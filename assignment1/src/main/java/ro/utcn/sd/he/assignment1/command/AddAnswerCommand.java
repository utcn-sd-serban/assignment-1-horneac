package ro.utcn.sd.he.assignment1.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddAnswerCommand implements Command {
    private Answer answer;

    @Override
    public void execute(RepositoryFactory factory) {
        this.answer = factory.createAnswerRepository().save(this.answer);

    }
}
