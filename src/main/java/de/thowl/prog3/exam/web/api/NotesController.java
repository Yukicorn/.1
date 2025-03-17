package de.thowl.prog3.exam.web.api;


import de.thowl.prog3.exam.service.NotesService;
import de.thowl.prog3.exam.service.impl.NotesServiceImpl;
import de.thowl.prog3.exam.storage.entities.Notes;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final NotesService noteSvc;

    public NotesController(NotesService noteSvc){
        this.noteSvc=noteSvc;
    }

    @PostMapping("/createNote")
    public ResponseEntity<?> createNote(@RequestBody Notes note , HttpSession session) throws DataNotFoundException {
        Notes savedNote = noteSvc.saveNote(note, session);
        return ResponseEntity.ok(savedNote);
    }

    //hier weitere Endpunkte. Abrufen, Aktualisieren, Löschen von Notizen

}
