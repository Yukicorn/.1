package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.service.impl.NotesServiceImpl;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.thowl.prog3.exam.web.gui.form.CreateNoteForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CreateNoteController {

    @Autowired
    NotesServiceImpl svc;
    @Autowired
    UserService usvc;

    @GetMapping("/createNote")
    public String createNoteForm(){
        log.debug("entering createNoteForm");
        return "createNote";//erstellen name etc und dann neue seite um inhalt zu bearbeiten
    }

    @PostMapping("/createNote")
    public String createNote(Model model, CreateNoteForm formdata, HttpSession session) throws DataNotFoundException {
        log.debug("processing createNoteForm");

        if (session == null) {
            log.error("HttpSession ist NULL! Die Session wurde nicht korrekt verwaltet.");
            throw new DataNotFoundException("Session existiert nicht.");
        }

        User user= usvc.getUserFromSession(session);

        // Hole den aktuell authentifizierten Benutzer aus der Session
        //User user = (User) session.getAttribute("user");

        if (user == null) {
            log.error("Benutzer ist Null");
            throw new DataNotFoundException("Benutzer nicht in der Session gefunden.");
        }

        log.debug("Benutzer aus der Session: "+user.getName());

        String noteTitel = formdata.getTitel();
        Notes note = new Notes();
        note.setTitle(noteTitel);

        log.debug("Titel: " + noteTitel);
        //log.debug("User: " + user.getUser(user));

        note.setUser(user); // Setze den Benutzer in die Notiz
        svc.saveNote(note, session);

        return "noteOverview";
    }

}
