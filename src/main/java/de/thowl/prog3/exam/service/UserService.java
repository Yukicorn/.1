package de.thowl.prog3.exam.service;

import java.util.List;

import de.thowl.prog3.exam.storage.entities.User;
import jakarta.servlet.http.HttpSession;

public interface UserService {

    public default User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user;
    }

    public User getUser(long id);

    public User getUser(String name);

    public List<User> getAllUsers();

}