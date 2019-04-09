package ro.utcn.sd.he.assignment1.persistence.api;

import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByID(int id);

    void remove(User user);

    User getAuthorOf(Question question);        //could make a parent class Post for Question and Answer so no overloading is needed

    User getAuthorOf(Answer answer);

    Optional<User> findByUsername(String username);

    List<User> findALL();
}
