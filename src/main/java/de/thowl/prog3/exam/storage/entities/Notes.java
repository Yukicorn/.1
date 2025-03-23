package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Diese Entität wird verwendet, um Notizen zu speichern, die Benutzer erstellen.
 * Jede Notiz hat einen Titel, Inhalt, Tags, das Erstellungsdatum, einen Typ, ein Bild und eine Freigabe-URL.
 * @Author Monique Rausche, Celeste Holsteg
 * @version 21.03.2025
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notes") // Gibt den Namen der Tabelle in der Datenbank an
public class Notes {

    /**
     * ID der Notiz, wird automatisch von der Datenbank generiert.
     */
    @Id //Primärschlüssel
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID wird automatisch von der Datenbank generiert
    private Long id;

    /**
     * Titel der Notiz
     */
    @Column(nullable = false) // "title" darf nicht null sein
    private String title;

    /**
     * Der Inhalt der Notiz
     */
    @Column(columnDefinition = "TEXT") // der Inhalt kann bei TEXT länger sein kann als VARCHAR
    private String content;

    /**
     * Tags, die der Notiz zugewiesen sind.
     */
    private String tags;

    /**
     * Erstellungsdatum der Notiz. Standardmäßig wird das aktuelle Datum verwendet.
     */
    private LocalDate createdAt = LocalDate.now(); // Setzt das Erstellungsdatum auf das aktuelle Datum

    /**
     * Typ der Notiz (Text, Bild, Link). Wird als ENUM gespeichert.
     */
    @Enumerated(EnumType.STRING)
    private NoteType type;

    /**
     * Der Benutzer, der die Notiz erstellt hat.
     * Jede Notiz gehört zu genau einem Benutzer.
     */
    @ManyToOne // Eine Notiz gehört zu genau einem Benutzer
    @JoinColumn(name = "user_id", nullable = false) // Spalte "user_id" in der Tabelle verweist auf den Benutzer
    private User user;

    /**
     * Kategorie, der die Notiz zugeordnet ist
     */
    @ManyToOne // Eine Notiz gehört zu genau einer Kategorie
    @JoinColumn(name = "category_id", nullable = true) // "category_id" in der Tabelle verweist auf die Kategorie
    private Category category;

    /**
     * Ein Bild, das vom Benutzer hochgeladen wird, wird in ein als Byte-Array umgewandelt
     * Wird als LONGBLOB gespeichert, um größere Binärdaten zu unterstützen.
     */
    @Lob
    @Column(columnDefinition = "LONGBLOB") // LONGBLOB für große Bilddateien verwendet
    private byte[] image; // Das Byte-Array

    /**
     * Freigabelink, als eindeutige GUID um die Notiz zu teilen
     */
    @Column(name = "shareable_link", unique = true) // Der Freigabelink muss einzigartig sein
    private String shareableLink; // GUID für den Freigabelink

}