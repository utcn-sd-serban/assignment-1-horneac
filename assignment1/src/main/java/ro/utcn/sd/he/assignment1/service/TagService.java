package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TagService {
    private final RepositoryFactory factory;

    @Transactional
    public Tag saveTag(Tag tag) {
        Optional<Tag> foundTag = factory.createTagRepository().findByName(tag.getName());
        if (foundTag.isPresent()) {
            return foundTag.get();
        } else {
            tag = factory.createTagRepository().save(tag);
            return tag;
        }
    }

    @Transactional
    public Tag getTag(String name) {
        Optional<Tag> tag = factory.createTagRepository().findByName(name);
        return tag.isPresent() ? tag.get() : new Tag(0, "");
    }

    @Transactional
    public List<Tag> listTags() {
        return factory.createTagRepository().findAll();
    }

}
