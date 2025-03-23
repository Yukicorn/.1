package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Diese Entity wird verwendet um Benutzer zu verwalten. Sie enthält Informationen
 * eines Benutzers, wie Benutzername, Passwort und E-Mail-Adresse.
 *
 * @author Monique Rausche, Celeste Holsteg
 * @Version 21.03.2025
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")  // Gibt den Tabellennamenin der Datenbank an
public class User {

    /**
     * ID des Benutzers, wird automatisch von der Datenbank generiert
     * Die ID wird durch eine Sequenz erzeugt, die bei `4` beginnt und bei jedem neuen Benutzer um 1 erhöht wird.
     *
     * @param id - Benutzer-ID.
     */
    @Id  // Primärschlüssel
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "users_seq")  // Gibt an, dass die ID von der Datenbank automatisch generiert wird
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", initialValue = 4, allocationSize = 1)  // Definiert eine Sequenz zur ID-Generierung
    private Long id;

    /**
     *Benutzername des Benutzers, der für die Authentifizierung verwendet wird.
     * @param name Der Benutzername des Benutzers.
     */
    @Column(name = "username")  // Gibt den Namen der Spalte in der Datenbank an
    private String name;

    /**
     * Das Passwort des Benutzers
     *
     * @param password Das Passwort des Benutzers.
     */
    @Column(name = "password")  // Gibt den Namen der Spalte in der Datenbank an
    private String password;

    /**
     * E-Mail-Adresse des Benutzers
     *
     * @param email E-Mail-Adresse des Benutzers.
     */
    @Column(name = "email")  // Gibt den Namen der Spalte in der Datenbank an
    private String email;

}