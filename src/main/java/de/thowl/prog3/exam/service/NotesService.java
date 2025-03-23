package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import jakarta.servlet.http.HttpSession;

/**
 * Service-Schnittstelle für Notizenverwaltung.
 * Diese Schnittstelle definiert Methoden für das Speichern von Notizen
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
public interface NotesService {

    /**
     * Diese Methode speichert eine neue Notiz.
     *
     * @param note Die Notiz, die gespeichert werden soll
     * @param session aktuelle HTTP-Session, die Benutzerdaten enthält
     * @param categoryId ID der Kategorie, der die Notiz zugeordnet werden soll
     * @return  gespeicherte Notiz
     * @throws DataNotFoundException falls nötige Daten nicht gefunden werden
     */
    public Notes saveNote(Notes note, HttpSession session, Long categoryId) throws DataNotFoundException;
}
