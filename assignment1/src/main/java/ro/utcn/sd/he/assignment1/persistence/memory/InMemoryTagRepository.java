package ro.utcn.sd.he.assignment1.persistence.memory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.persistence.api.TagRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InMemoryTagRepository implements TagRepository {
    private final Map<Integer,Tag> repo = new ConcurrentHashMap<Integer,Tag>();
    private AtomicInteger currentId = new AtomicInteger(1);


    @Override
    public Tag save(Tag tag) {
        if(tag.getId() == 0){
            tag.setId(currentId.getAndIncrement());
            repo.put(tag.getId(),tag);
        } else {
            repo.put(tag.getId(),tag);
        }
        return tag;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return Optional.ofNullable(getByName(name));
    }

    @Override
    public List<Tag> findAll() {
        return new ArrayList<Tag>(repo.values());
    }

    public Tag getByName(String name){

        for(Tag t: repo.values()){
            if(t.getName() == name){
                return t;
            }
        }
        return null;
    }
}
