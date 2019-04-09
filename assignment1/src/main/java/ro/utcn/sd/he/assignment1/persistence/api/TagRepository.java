package ro.utcn.sd.he.assignment1.persistence.api;

import ro.utcn.sd.he.assignment1.model.Tag;

import java.util.List;
import java.util.Optional;


public interface TagRepository {
    Tag save(Tag tag);

    Optional<Tag> findByName(String name);

    List<Tag> findAll();
}
