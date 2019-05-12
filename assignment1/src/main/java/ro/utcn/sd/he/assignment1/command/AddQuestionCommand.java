package ro.utcn.sd.he.assignment1.command;

import lombok.Data;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import java.util.Optional;

@Data
public class AddQuestionCommand implements Command {
    private Question question;
    private String tags;

    @Override
    public void execute(RepositoryFactory factory) {
        this.question = factory.createQuestionRepository().save(this.question);
        for (String tag : tags.split(" ")) {
            Optional<Tag> t = factory.createTagRepository().findByName(tag);
            if (t.isPresent()) {
                factory.createQuestionTagRepository().insert(this.question, t.get());
            } else {
                Tag newTag = factory.createTagRepository().save(new Tag(0, tag));
                factory.createQuestionTagRepository().insert(this.question, newTag);

            }
        }


    }
}
