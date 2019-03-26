package ro.utcn.sd.he.assignment1.persistence.memory;

import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.persistence.api.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryUserRepository implements UserRepository {
    private final Map<Integer, User> data = new ConcurrentHashMap<>();
    private AtomicInteger currentId = new AtomicInteger(1);

    @Override
    public User getAuthorOf(Answer answer) {
        String username = answer.getAuthor();
        User user = findByUsername(username).get();
        return user;


    }

    @Override
    public User getAuthorOf(Question question) {
        String username = question.getAuthor();
        User user = findByUsername(username).get();
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        for (User user : data.values()) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        if (user.getId() == 0) { //new user => insert
            user.setId(currentId.getAndIncrement());
            data.put(user.getId(), user);
        } else {
            data.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public Optional<User> findByID(int id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public void remove(User user) {
        data.remove(user.getId());
    }

    @Override
    public List<User> findALL() {
        return new ArrayList<>(data.values());
    }
}
