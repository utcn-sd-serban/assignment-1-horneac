package ro.utcn.sd.he.assignment1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.he.assignment1.dto.UserDTO;
import ro.utcn.sd.he.assignment1.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping("users/{id}")
    public UserDTO readSingle(@PathVariable int id) {
        //TODO
        return null;
    }

    @GetMapping("/users")
    public List<UserDTO> readAll() {
        return userService.listUsers();
    }

    @PostMapping("/users")
    public UserDTO create(@RequestBody UserDTO dto) {
        return userService.saveUser(dto);
    }
}
