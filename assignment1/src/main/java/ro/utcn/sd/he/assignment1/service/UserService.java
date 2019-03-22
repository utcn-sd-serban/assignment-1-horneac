package ro.utcn.sd.he.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.persistence.api.RepositoryFactory;
import ro.utcn.sd.he.assignment1.persistence.api.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserService {
    private final RepositoryFactory factory;

    @Transactional
    public List<User> listUsers(){
        UserRepository repo = factory.createUserRepository();
        return repo.findALL();
    }

    @Transactional
    public User saveUser(User user){
        return factory.createUserRepository().save(user);
    }

    @Transactional
    public void deleteUser(int id){
        Optional<User> user = factory.createUserRepository().findByID(id);
        factory.createUserRepository().remove(user.get());
    }

    @Transactional
    public User logIn(String username, String password){
        Optional<User> user = factory.createUserRepository().findByUsername(username);
        if(password.equals(user.get().getPassword())){
            return user.get();
        } else {
            return null;
        }
    }
}
