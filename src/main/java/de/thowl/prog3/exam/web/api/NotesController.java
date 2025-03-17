package de.thowl.prog3.exam.web.api;


import de.thowl.prog3.exam.service.NotesService;
import de.thowl.prog3.exam.service.impl.NotesServiceImpl;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.repositories.NotesRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/notes")
public class NotesController {

    private final NotesService noteSvc;
    @Autowired
    private final NotesRepository noteRepo;
    @Autowired
    private final NotesServiceImpl noteSvcImpl;

    public NotesController(NotesService noteSvc, NotesRepository noteRepo, NotesServiceImpl noteSvcImpl) {
        this.noteSvc = noteSvc;
        this.noteRepo = noteRepo;
        this.noteSvcImpl = noteSvcImpl;
    }

    @PostMapping("/createNote")
    public String saveNote(@ModelAttribute Notes note, @RequestParam("imageFile") MultipartFile file, HttpSession session) throws IOException, DataNotFoundException {
        // Prüfen, ob eine Datei hochgeladen wurde
        if (!file.isEmpty()) {
            // Bild als Byte-Array setzen
            note.setImage(file.getBytes());
            log.info("Bild empfangen: " + file.getOriginalFilename());
        } else {
            log.error("Kein Bild hochgeladen!");
        }

        // Speichern der Notiz
        noteSvc.saveNote(note, session);

        // Weiterleitung zur Seite, z.B. eine Bestätigung oder zur Anzeige der Notizen
        return "noteOverview";  // Weiterleitung nach dem Speichern (hier der View-Name)
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        Optional<Notes> note = noteRepo.findById(id);
        if (note.isPresent() && note.get().getImage() != null) {
            String contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(note.get().getImage()));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(note.get().getImage());
        }
        return ResponseEntity.notFound().build();
    }



}
