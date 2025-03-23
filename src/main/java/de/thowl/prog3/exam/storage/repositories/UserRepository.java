package de.thowl.prog3.exam.storage.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import de.thowl.prog3.exam.storage.entities.User;

/**
 * Dieses Repository verwaltet Datenbankabfragen für Benutzer und ermöglicht
 * das Speichern, Abrufen und Suchen von Benutzerdaten.
 *
 * @author Monique Rausche, Celeste Holsteg
 * @version 21.03.2025
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Sucht einen Benutzer anhand seiner ID.
     *
     * @param id ID des gesuchten Benutzers.
     * @return Optional-Objekt, das den Benutzer enthält, falls er existiert.
     */
    Optional<User> findUserById(long id);

    /**
     * Sucht einen Benutzer anhand seines Benutzernamens.
     *
     * @param username Benutzername des gesuchten Benutzers.
     * @return Optional-Objekt, das den Benutzer enthält, falls er existiert.
     */
    Optional<User> findUserByName(String username);

    /**
     * Sucht einen Benutzer anhand seiner E-Mail-Adresse.
     *
     * @param email E-Mail-Adresse des gesuchten Benutzers.
     * @return Optional-Objekt, das den Benutzer enthält, falls er existiert.
     */
    Optional<User> findUserByEmail(String email);
}