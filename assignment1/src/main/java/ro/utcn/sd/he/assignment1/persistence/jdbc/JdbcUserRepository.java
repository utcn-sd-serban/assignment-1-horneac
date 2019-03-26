package ro.utcn.sd.he.assignment1.persistence.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.persistence.api.UserRepository;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate template;
    private final RowMapper<User> rowMapper = ((resultSet, i) -> new User(
            resultSet.getInt("id"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getString("type"),
            resultSet.getBoolean("banned"),
            resultSet.getInt("score")
    ));

    @Override
    public User getAuthorOf(Answer answer) {
        String username = answer.getAuthor();
        User user = findByUsername(username).get();
        return user;

    }

    @Override
    public User save(User user) {
        if(user.getId() == 0){      //new user
            int id = insert(user);
            user.setId(id);
        } else {
            update(user);
        }
        return user;
    }

    @Override
    public Optional<User> findByID(int id) {
        List<User> users =  template.query("SELECT * FROM user WHERE id = ?",
                rowMapper,
                id);
        return users.isEmpty()? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public void remove(User user) {
        template.update("DELETE FROM user WHERE id = ?", user.getId());
    }

    /*
    @param question
    @return user instance of the author of the question
     */
    @Override
    public User getAuthorOf(Question question) {
        String username = question.getAuthor();
        User user =  findByUsername( username).get();
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> user = template.query("SELECT * FROM user WHERE username = ?",
                (resultSet, i) -> new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("type"),
                        resultSet.getBoolean("banned"),
                        resultSet.getInt("score")
                ),
                username);
        return user.isEmpty()? Optional.empty() : Optional.of(user.get(0));
    }

    @Override
    public List<User> findALL() {
        List<User> users =  template.query("SELECT * FROM user",
                (resultSet, i) -> new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("type"),
                        resultSet.getBoolean("banned"),
                        resultSet.getInt("score")
                ));
        return users;
    }

    private int insert(User user){
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("user");
        insert.setGeneratedKeyName("id");
        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("password", user.getPassword());
        data.put("type", user.getType());
        data.put("banned", user.isBanned());        //this is cool lombok ;)
        data.put("score", user.getScore());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(User user){
        template.update("UPDATE user SET username = ?, password = ?, type = ?, banned = ?, score = ? where id = ?",
                user.getUsername(),
                user.getPassword(),
                user.getType(),
                user.isBanned(),
                user.getScore(),
                user.getId());
    }
}
