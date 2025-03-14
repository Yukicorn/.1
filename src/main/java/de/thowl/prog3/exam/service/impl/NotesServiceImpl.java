package de.thowl.prog3.exam.service.impl;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import de.thowl.prog3.exam.service.NotesService;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.repositories.NotesRepository;
import de.thowl.prog3.exam.storage.repositories.UserRepository;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotesServiceImpl implements NotesService{

    private final NotesRepository noteRepository;
    private final UserRepository userRepository;

    public NotesServiceImpl(NotesRepository noteRepository, UserRepository userRepository){
        this.noteRepository=noteRepository;
        this.userRepository=userRepository;
    }


    /*@Override
    public Notes saveNote(Notes note, String username) throws DataNotFoundException {
        // Finde den aktuell authentifizierten Nutzer
        User user = userRepository.findUserByName(username)
                .orElseThrow(() -> new DataNotFoundException("Nutzer nicht vorhanden."));

        // Setze den Nutzer für die Notiz
        note.setUser(user);
        log.debug("Nutzer ist:" +user);

        // Speichere die Notiz
        log.debug("Notiztitel ist:" +note);
        return noteRepository.save(note);
    }*/

    @Override
    public Notes saveNote(Notes note, HttpSession session) throws DataNotFoundException {
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
        return noteRepository.save(note);
    }

}
