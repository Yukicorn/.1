package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notes")
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY ) //erzeuge ID automatisch
    private Long id;

    @Column(nullable = false) // darf nicht leer sein
    private String title;

    @Column(columnDefinition = "TEXT") //TEXT kann längere Texte speichern als Varchar sinnvoll?
    private String content;
    private String tags;
    private LocalDateTime createdAt = LocalDateTime.now(); // speichere datum, uhrzeit notiz erstellt

    @Enumerated(EnumType.STRING)  // Art als ENUM speichern
    private NoteType type;

    @ManyToOne  // Eine Notiz gehört zu einem Benutzer
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Lob
    @Column(columnDefinition = "LONGBLOB")  // Für große Binärdateien
    private byte[] image;  // Hier wird das Bild als Byte-Array gespeichert
}
