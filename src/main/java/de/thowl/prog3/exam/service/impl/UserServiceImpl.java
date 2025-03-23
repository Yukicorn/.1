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

/**
 * Service-Klasse zur Verwaltung der Benutzer.
 * Diese Klasse enthält Methoden zum Registrieren und Authentifizieren der Benutzer.
 * Sie implementiert die Klasse UserService.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    /**
     * Die Methode holt einen Benutzer anhand der ID.
     * Wirft eine IllegalArgumentException, wenn der Benutzer nicht gefunden wird.
     *
     * @param id ID des Benutzers
     * @return gefundener Benutzer
     */
    @Override
    public User getUser(long id) {
        log.debug("entering getUser(id={})", id);
        Optional<User> result = this.repository.findById(id);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Die Methode holt einen Benutzer anhand des Benutzernamens.
     * Wirft eine IllegalArgumentException, wenn der Benutzer nicht gefunden wird.
     *
     * @param name Name des Benutzers
     * @return gefundener Benutzer
     */
    @Override
    public User getUser(String name) {
        log.debug("entering getUser(name={})", name);
        Optional<User> result = this.repository.findUserByName(name);
        return result.orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Die Methode holt alle Benutzer aus der Datenbank.
     *
     * @return Liste aller Benutzer
     */
    @Override
    public List<User> getAllUsers() {
        log.debug("entering getAllUsers()");
        ArrayList<User> result = new ArrayList<>();
        for (User u : this.repository.findAll()) {
            result.add(u);
        }
        return result;
    }

    /**
     * Die Methode registriert einen neuen Benutzer mit Benutzernamen, Passwort und E-Mail.
     * Zudem werden doppelte Benutzernamen verhindert und der Benutzer nach dem Hashen des Passworts gespeichert.
     *
     * @param username Benutzername des neuen Benutzers
     * @param password Passwort des neuen Benutzers
     * @param email E-Mail-Adresse des neuen Benutzers
     */
    @Transactional//Stellt sicher, dass die Operation atomar ist
    public void registerUser(String username, String password, String email) {
        log.debug("entering registerUser(username={}, email={})", username, email);

        //Prüfen ob Benutzer bereits existiert
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

    /**
     * Die Methode authentifiziert einen Benutzer anhand des Benutzernamens und des Passworts.
     *
     * @param username Benutzername des Benutzers
     * @param password Passwort des Benutzers
     * @return authentifizierter Benutzer
     */
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