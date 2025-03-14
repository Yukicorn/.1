package de.thowl.prog3.exam.web.api;


import de.thowl.prog3.exam.service.impl.NoteServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteServiceImpl noteSvc;

    public NoteController(NoteServiceImpl noteSvc){
        this.noteSvc=noteSvc;
    }

    @PostMapping("/createNote")
    public ResponseEntity<?> createNote(@RequestBody Note , Principal principal){
        Note savedNote = noteSvc.saveNote(note, principal.getName());
        return ResponseEntity.ok(savedNote);
    }

    //hier weitere Endpunkte. Abrufen, Aktualisieren, Löschen von Notizen

}
