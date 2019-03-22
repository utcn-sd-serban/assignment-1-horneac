package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TagService {
    private final RepositoryFactory factory;

    public Tag saveTag(Tag tag){
        tag = factory.createTagRepository().save(tag);
        return tag;
    }

    public Tag getTag(String name){
        return factory.createTagRepository().findByName(name).get();
    }

    public List<Tag> listTags(){
        return factory.createTagRepository().findAll();
    }

}
