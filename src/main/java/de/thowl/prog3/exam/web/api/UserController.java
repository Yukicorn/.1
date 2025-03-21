package de.thowl.prog3.exam.web.api;

import java.util.ArrayList;
import java.util.List;

import de.thowl.prog3.exam.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();

    @Autowired
    private UserService service;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        log.debug("entering ctor");
    }

    @GetMapping("/")
    public List<de.thowl.prog3.exam.web.dto.User> getUsers() {
        log.debug("entering getUsers");
        List<de.thowl.prog3.exam.web.dto.User> result = new ArrayList<>();
        List<User> users = this.service.getAllUsers();
        for (User u : users) {
            result.add(this.mapper.map(u));
        }
        return result;
    }

    @GetMapping("/{id}")
    public de.thowl.prog3.exam.web.dto.User getUserbyId(@PathVariable Long id) {
        log.debug("entering getUserById, id={}", id);
        User u = this.service.getUser(id);
        return this.mapper.map(u);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userServiceImpl.getUser(user.getName()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        // Registrierung des Benutzers mit gehashtem Passwort
        userServiceImpl.registerUser(user.getName(), user.getPassword(), user.getEmail());
        return ResponseEntity.ok("User successfully registered.");
    }
}

