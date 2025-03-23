package de.thowl.prog3.exam.web.api;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.service.NotesService;
import de.thowl.prog3.exam.service.impl.NotesServiceImpl;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.NoteType;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.NotesRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.time.LocalDate;
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
    @Autowired
    private final CategoryService categoryService;

    public NotesController(NotesService noteSvc, NotesRepository noteRepo, NotesServiceImpl noteSvcImpl, CategoryService categoryService) {
        this.noteSvc = noteSvc;
        this.noteRepo = noteRepo;
        this.noteSvcImpl = noteSvcImpl;
        this.categoryService=categoryService;
    }

    @PostMapping("/createNote")
    public String saveNote(@ModelAttribute Notes note, @RequestParam("imageFile") MultipartFile file, HttpSession session, Long categoryId) throws IOException, DataNotFoundException {
        // Prüfen, ob eine Datei hochgeladen wurde
        if (!file.isEmpty()) {
            // Bild als Byte-Array setzen
            note.setImage(file.getBytes());
            log.info("Bild empfangen: " + file.getOriginalFilename());
        } else {
            log.error("Kein Bild hochgeladen!");
        }

        // Speichern der Notiz
        noteSvc.saveNote(note, session, categoryId);

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

    @GetMapping("/filter")
    public ResponseEntity<List<Notes>> filterNotes(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) NoteType type,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate createdAt,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate toDate,
            HttpSession session) throws DataNotFoundException {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // Benutzer ist nicht eingeloggt
        }

        // Hole die Kategorie, falls eine ID übergeben wurde
        Category category = null;
        if (categoryId != null) {
            // Stelle sicher, dass der Category-Service das Category-Objekt anhand der ID zurückgibt
            category = categoryService.getCategoryById(categoryId);
        }

        // Logge die Filterparameter
        log.debug("Filtern mit den folgenden Parametern - tag: {}, type: {}, category: {}, startDate: {}, endDate: {}",
                tag, type, categoryId, createdAt, toDate);


        List<Notes> filteredNotes = noteSvcImpl.filterNotes(user, tag, type, categoryId, createdAt, toDate);
        if (filteredNotes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Keine Notizen gefunden
        }

        return ResponseEntity.ok(filteredNotes);
    }


}
