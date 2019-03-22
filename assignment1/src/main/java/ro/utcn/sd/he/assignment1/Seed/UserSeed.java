package ro.utcn.sd.he.assignment1.Seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ro.utcn.sd.he.assignment1.model.User;
import ro.utcn.sd.he.assignment1.service.UserService;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserSeed implements CommandLineRunner {
    private final UserService service;

    @Override
    public void run(String... args) throws Exception {
        if(service.listUsers().isEmpty()){
            service.saveUser(new User(0,"horneac", "123456","user", false,0));
            service.saveUser((new User(0,"admin","admin","moderator",false,0)));
        }
    }
}
