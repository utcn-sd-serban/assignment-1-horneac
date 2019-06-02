package ro.utcn.sd.he.assignment1.command;

import lombok.NoArgsConstructor;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

@NoArgsConstructor
public class EditAnswerCommand implements Command {
    private Answer oldAnswer;
    private Answer newAnswer;

    @Override
    public void execute(RepositoryFactory factory) {
        this.oldAnswer = factory.createAnswerRepository().findById(this.newAnswer.getId()).get();
        this.newAnswer = factory.createAnswerRepository().save(this.newAnswer);

    }

    public Answer getNewAnswer() {
        return this.newAnswer;
    }

    public void setNewAnswer(Answer answer) {
        this.newAnswer = answer;
    }

    public Answer getOldAnswer() {
        return this.oldAnswer;
    }

}
