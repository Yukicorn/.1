package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.NoteType;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import de.thowl.prog3.exam.service.NotesService;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.repositories.NotesRepository;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service-Klasse zur Verwaltung von Notizen.
 * Diese Klasse enthält Methoden zum Speichern und Filtern von Notizen.
 * Sie implementiert die Klasse NotesService.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Slf4j
@Service
public class NotesServiceImpl implements NotesService{

    private final NotesRepository noteRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    /**
     * Konstruktor für NotesServiceImpl. Initialisiert die benötigten Repositories und Services.
     *
     * @param noteRepository Repository für Notizen
     * @param userRepository Repository für Benutzer
     * @param categoryService Service für Kategorien
     */
    public NotesServiceImpl(NotesRepository noteRepository, UserRepository userRepository, CategoryService categoryService){
        this.noteRepository=noteRepository;
        this.userRepository=userRepository;
        this.categoryService=categoryService;
    }

    /**
     * Diese Methode speichert eine neue Notiz, setzt den Benutzer und erstellt einen Freigabelink.
     * Zudem wird die Notiz einer Kategorie zugewiesen.
     *
     * @param note Notiz, die gespeichert werden soll
     * @param session HTTP-Session, des aktuellen Benutzers
     * @param categoryId ID der Kategorie, die der Notiz zugewiesen werden soll
     * @return gespeicherte Notiz
     * @throws DataNotFoundException Falls der Benutzer nicht in der Session gefunden wird
     */
    @Override
    public Notes saveNote(Notes note, HttpSession session, Long categoryId) throws DataNotFoundException {
        // Abrufen des aktuellen Benutzers aus der Session
        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new DataNotFoundException("Benutzer nicht in der Session gefunden.");
        }

        // Setzt den Benutzer für die Notiz
        note.setUser(user);
        log.debug("Aktuell authentifizierter Nutzer ist: " + user);

        log.debug("Notiztitel ist: " + note.getTitle());

        log.debug("Image "+note.getImage());

        // GUID für den Freigabelink generieren und zuweisen
        String generatedLink = UUID.randomUUID().toString();
        note.setShareableLink(generatedLink); // Speichern der GUID

        //Kategorie für die Notiz setzen
        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                note.setCategory(category);
            } else {
                log.warn("Kategorie mit ID {} nicht gefunden!", categoryId);
            }
        } else {
            log.warn("categoryId ist null!");
        }

        // Speichert die Notiz
        return noteRepository.save(note);
    }

    /**
     * Diese Methode gibt alle Notizen des angemeldeten Benutzers zurück.
     *
     * @param session HTTP-Session, um aktuellen Benutzer zu erhalten
     * @return Liste der Notizen des aktuellen Benutzers
     * @throws DataNotFoundException Falls der Benutzer nicht in der Session gefunden wird
     */
    public List<Notes> getAllNotesForCurrentUser(HttpSession session) throws DataNotFoundException {
        // Benutzer aus der Session holen
        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new DataNotFoundException("Kein Benutzer in der Session gefunden.");
        }

        // Lädt nur die Notizen des aktuellen Benutzers
        return (List<Notes>) noteRepository.findByUser(user);
    }

    /**
     * Diese Methode filtert Notizen nach verschiedenen Kriterien wie Tag, Typ, Kategorie und Erstellungsdatum.
     *
     * @param user Benutzer, dessen Notizen gefiltert werden
     * @param tag Tag, nach dem gefiltert werden soll (optional)
     * @param type Typ der Notiz (optional)
     * @param categoryId ID der Kategorie, nach der gefiltert werden soll (optional)
     * @param createdAt Erstellungsdatum, nach dem gefiltert werden soll (optional)
     * @param toDate Enddatum, nach dem gefiltert werden soll (optional)
     * @return Eine Liste der gefilterten Notizen
     * @throws DataNotFoundException Wenn keine Notizen gefunden werden
     */
    public List<Notes> filterNotes(User user, String tag, NoteType type, Long categoryId, LocalDate createdAt, LocalDate toDate) throws DataNotFoundException {
        //Gross/Kleinschreibung ignorieren
        if (tag != null) {
            tag = tag.toLowerCase();
        }

        //falls angegeben, Kategorie fürs Filtern
        Category category = null;
        if (categoryId != null) {
            category = categoryService.getCategoryById(categoryId);
        }

        log.debug("Filter: tags={}, type={}, categoryId={}, createdAt={}, toDate={}",
                tag, type, categoryId, createdAt, toDate);

        // Filtern nur für den angegebenen Benutzer
        List<Notes> filteredNotes = noteRepository.filterNotes(user, tag, type, category, createdAt, toDate);

        //Liste der gefilterten Notizen zurückgeben
        return filteredNotes;
    }

}
