package de.thowl.prog3.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User getUser(long id) {
        log.debug("entering getUser(id={})", id);
        Optional<User> result = this.repository.findById(id);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public User getUser(String name) {
        log.debug("entering getUser(name={})", name);
        Optional<User> result = this.repository.findUserByName(name);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<User> getAllUsers() {
        log.debug("entering getAllUsers()");
        ArrayList<User> result = new ArrayList<>();
        for (User u : this.repository.findAll()) {
            result.add(u);
        }
        return result;
    }

    public User registerUser (User user){
        return repository.save(user);
    }
@Transactional
    public void registerUser(String username, String password, String email) {
        log.debug("entering registerUser(username={}, email={})", username, email);

        // Benutzer anhand des Benutzernamens finden (doppelte Benutzernamen verhindern)
        Optional<User> existingUser = repository.findUserByName(username);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Benutzername bereits vergeben");
        }

        // Benutzer erstellen und Passwort hashen
        User user = new User();
        user.setName(username);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // Passwort hashen
        user.setPassword(hashedPassword);
        user.setEmail(email);

        // Benutzer in der DB speichern
        repository.save(user);
        log.debug("Benutzer {} erfolgreich registriert", username);
    }


    public User authenticate(String username, String password) {
        log.debug("entering authenticate(username={})", username);

        // Benutzer anhand des Namens finden
        User user = this.repository.findUserByName(username)
                .orElseThrow(() -> new IllegalArgumentException("Benutzer nicht gefunden"));

        // Passwort prüfen
        if (BCrypt.checkpw(password, user.getPassword())) {
            return user;  // Benutzer ist authentifiziert
        } else {
            throw new IllegalArgumentException("Falsches Passwort");
        }
    }
}