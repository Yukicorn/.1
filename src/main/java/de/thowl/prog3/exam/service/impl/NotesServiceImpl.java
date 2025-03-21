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

@Slf4j
@Service
public class NotesServiceImpl implements NotesService{

    private final NotesRepository noteRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;

    public NotesServiceImpl(NotesRepository noteRepository, UserRepository userRepository, CategoryService categoryService){
        this.noteRepository=noteRepository;
        this.userRepository=userRepository;
        this.categoryService=categoryService;
    }

    @Override
    public Notes saveNote(Notes note, HttpSession session, Long categoryId) throws DataNotFoundException {
        // Abrufen des aktuellen Benutzers aus der Session
        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new DataNotFoundException("Benutzer nicht in der Session gefunden.");
        }

        // Setze den Benutzer für die Notiz
        note.setUser(user);
        log.debug("Aktuell authentifizierter Nutzer ist: " + user);

        // Speichere die Notiz
        log.debug("Notiztitel ist: " + note.getTitle());

        log.debug("Image "+note.getImage());

        Category category = categoryService.getCategoryById(categoryId);

        return noteRepository.save(note);
    }

    public List<Notes> getAllNotesForCurrentUser(HttpSession session) throws DataNotFoundException {
        // Benutzer aus der Session holen
        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new DataNotFoundException("Kein Benutzer in der Session gefunden.");
        }

        // Lade nur die Notizen des aktuellen Benutzers

        return (List<Notes>) noteRepository.findByUser(user);
    }

    public List<Notes> filterNotes(User user, String tag, NoteType type, Long categoryId, LocalDate createdAt, LocalDate toDate){
        if (tag != null) {
            tag = tag.toLowerCase();
        }

        // Filtern nur für den Benutzer
        List<Notes> filteredNotes = noteRepository.filterNotes(user, tag, type, categoryId, createdAt, toDate);
        return filteredNotes;

    }

}
