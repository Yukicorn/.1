package de.thowl.prog3.exam.storage.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Diese Entity wird verwendet, um Kategorien zu speichern, die Notizen in der Anwendung organisieren.
 * Jede Kategorie hat eine eindeutige ID und einen Namen.
 * @author Monique Rausche, Celeste Holsteg
 * @version 21.03.2025
 */
@Entity
@Data
@Table(name = "category") // Gibt den Namen der Tabelle in der Datenbank an
public class Category {

    /**
     * ID der Kategorie, wird automatisch von der Datenbank generiert.
     */
    @Id //Primärschlüssel
    @GeneratedValue //ID wird automatisch von der Datenbank generiert
    private Long id;

    /**
     * Name der Kategorie
     */
    @Column(name = "name") //Bennent die Spalte in der Tabelle
    private String name;



}