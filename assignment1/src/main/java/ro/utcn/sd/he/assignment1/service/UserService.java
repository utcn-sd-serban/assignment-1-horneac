package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.dto.UserDTO;
import ro.utcn.sd.he.assignment1.model.Answer;
import ro.utcn.sd.he.assignment1.model.Question;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;
import ro.utcn.sd.he.assignment1.persistence.api.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserService {
    private final RepositoryFactory factory;

    @Transactional
    public List<UserDTO> listUsers() {
        UserRepository repo = factory.createUserRepository();
        return repo.findALL().stream().map(UserDTO::ofEntity).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setScore(0);
        user.setType("user");
        user.setBanned(false);
        return UserDTO.ofEntity(factory.createUserRepository().save(user));
    }

    @Transactional
    public User saveUser(User user) {
        return factory.createUserRepository().save(user);
    }

    @Transactional
    public void deleteUser(int id) {
        Optional<User> user = factory.createUserRepository().findByID(id);
        factory.createUserRepository().remove(user.get());
    }

//    @Transactional
//    public User register(String username, String password) {
//        User user = new User(0, username, password, "user", false, 0);
//        user = saveUser(user);
//        return user;
//
//    }

    @Transactional
    public User logIn(String username, String password) {
        Optional<User> user = factory.createUserRepository().findByUsername(username);
        if (!user.isPresent()) {
            return null;
        }
        if (password.equals(user.get().getPassword())) {
            return user.get();
        } else {
            return null;
        }
    }

    @Transactional
    public User getAuthorOf(Object post) {
        if (post.getClass().getSimpleName().equals("Answer")) {
            return factory.createUserRepository().getAuthorOf((Answer) post);
        } else if (post.getClass().getSimpleName().equals("Question")) {
            return factory.createUserRepository().getAuthorOf((Question) post);
        } else {
            System.out.println("You can only get the author of an answer or question post!");
            return null;
        }
    }
}
