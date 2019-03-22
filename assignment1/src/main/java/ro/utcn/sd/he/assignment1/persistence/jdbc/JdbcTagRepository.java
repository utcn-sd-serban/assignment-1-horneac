package ro.utcn.sd.he.assignment1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.he.assignment1.model.Tag;
import ro.utcn.sd.he.assignment1.persistence.api.TagRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcTagRepository implements TagRepository {
    private final JdbcTemplate template;

    @Override
    public Tag save(Tag tag) {
        if(tag.getId() == 0){
            int id = insert(tag);
            tag.setId(id);
        } else {
            update(tag);
        }
        return tag;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> tags = template.query("SELECT * FROM tag WHERE name = ?",
                (resultSet, i) -> new Tag(resultSet.getInt("id"),
                                            resultSet.getString("name")),name);
        return tags.isEmpty()? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public List<Tag> findAll() {
        return template.query("SELECT * FROM tag",
                (resultSet, i) -> new Tag(resultSet.getInt("id"),
                                        resultSet.getString("name")));
    }

    private int insert(Tag tag){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("tag");
        insert.setGeneratedKeyName("id");
        Map<String, Object> data = new HashMap<>();
        data.put("name",tag.getName());
        return  insert.executeAndReturnKey(data).intValue();

    }

    private void update(Tag tag){
        template.update("UPDATE tag SET name = ? WHERE id = ?",
                tag.getName(),
                tag.getId());
    }
}
