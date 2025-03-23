package de.thowl.prog3.exam.service;

import java.util.List;

import de.thowl.prog3.exam.storage.entities.User;
import jakarta.servlet.http.HttpSession;

/**
 * Service-Schnittstelle für Benutzerverwaltung.
 * Diese Schnittstelle definiert Methoden für den Zugriff auf Benutzerinformationen.
 * Zudem eine Standardmethode zum Abrufen des Benutzers aus der aktuellen Sitzung.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
public interface UserService {

    /**
     * Methode zum Abrufen des aktuellen Benutzers aus der Session.
     *
     * @param session aktuelle HTTP-Session, aus der der Benutzer geladen wird.
     * @return Der angemeldete Benutzer oder `null`, falls kein Benutzer in der Session existiert.
     */
    public default User getUserFromSession(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user;
    }

    /**
     * Die Methode holt einen Benutzer anhand der eindeutigen ID.
     *
     * @param id eindeutige ID des Benutzers
     * @return `User`-Objekt oder `null`, falls kein Benutzer mit dieser ID existiert.
     */
    public User getUser(long id);

    /**
     * Die Methode holt einen Benutzer anhand seines Namens.
     *
     * @param name Benutzername
     * @return `User`-Objekt oder `null`, falls kein Benutzer mit diesem Namen existiert.
     */
    public User getUser(String name);

    /**
     * Die Methode holt eine Liste aller vorhandenen Benutzer.
     *
     * @return Liste aller registrierten Benutzer
     */
    public List<User> getAllUsers();

}