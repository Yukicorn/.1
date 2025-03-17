package de.thowl.prog3.exam.web.api;


import de.thowl.prog3.exam.service.NotesService;
import de.thowl.prog3.exam.service.impl.NotesServiceImpl;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.repositories.NotesRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final NotesService noteSvc;
    private final NotesRepository noteRepo;

    public NotesController(NotesService noteSvc, NotesRepository noteRepo) {
        this.noteSvc = noteSvc;
        this.noteRepo = noteRepo;
    }

    @PostMapping("/createNote")
    public ResponseEntity<?> createNote(@RequestBody Notes note, HttpSession session) throws DataNotFoundException {
        Notes savedNote = noteSvc.saveNote(note, session);
        return ResponseEntity.ok(savedNote);
    }

    @PostMapping("/saveNote")
    public String saveNote(@ModelAttribute Notes note, @RequestParam("imageFile") MultipartFile file, HttpSession session) throws IOException, IOException, DataNotFoundException {
        if (!file.isEmpty()) {
            note.setImage(file.getBytes());  // Bild als Byte-Array speichern
        }
        noteSvc.saveNote(note, session);
        return "noteSaved";  // Weiterleitung nach dem Speichern
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<Notes> note = noteRepo.findByID(id);
        if (note.isPresent() && note.get().getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  // Falls es ein JPEG ist, evtl. anpassen
                    .body(note.get().getImage());
        }
        return ResponseEntity.notFound().build();
        //hier weitere Endpunkte. Abrufen, Aktualisieren, Löschen von Notizen

    }
}
