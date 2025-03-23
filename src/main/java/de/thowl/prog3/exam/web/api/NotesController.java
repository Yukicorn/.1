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

/**
 * REST-Controller für das Verwalten der Notizen über eine API.
 * Dieser Controller ermöglicht das Erstellen, Filtern und Abrufen von Notizen.
 * Zudem wird die Verwaltung von Bilddateien für Notizen ermöglicht.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Slf4j
@RestController
@RequestMapping("/api/notes")// Basis-URL für alle Endpunkte dieses Controllers
public class NotesController {

    private final NotesService noteSvc;
    @Autowired
    private final NotesRepository noteRepo;
    @Autowired
    private final NotesServiceImpl noteSvcImpl;
    @Autowired
    private final CategoryService categoryService;

    /**
     * Konstruktor für den NotesController
     *
     * @param noteSvc Service für Notizen-Verwaltung
     * @param noteRepo Repository für den Zugriff auf die Notizen-Datenbank
     * @param noteSvcImpl Implementierung des Notizen-Services
     * @param categoryService Service für Kategorien
     */
    public NotesController(NotesService noteSvc, NotesRepository noteRepo, NotesServiceImpl noteSvcImpl, CategoryService categoryService) {
        this.noteSvc = noteSvc;
        this.noteRepo = noteRepo;
        this.noteSvcImpl = noteSvcImpl;
        this.categoryService=categoryService;
    }

    /**
     * Diese Methode speichert eine neue Notiz und verarbeitet ein hochgeladenes Bild.
     *
     * @param note Notiz-Objekt mit den Benutzereingaben
     * @param file Hochgeladene Bilddatei
     * @param session HTTP-Session, des angemeldeten Benutzers
     * @param categoryId ID der Kategorie, zu der die Notiz gehört
     * @return Template für die Notizübersicht nach erfolgreichem Speichern
     * @throws IOException Falls ein Fehler beim Verarbeiten des Bildes auftritt
     * @throws DataNotFoundException Falls benötigte Daten nicht gefunden werden
     */
    @PostMapping("/createNote")
    public String saveNote(@ModelAttribute Notes note, @RequestParam("imageFile") MultipartFile file, HttpSession session, Long categoryId) throws IOException, DataNotFoundException {
        // Prüft, ob eine Datei hochgeladen wurde
        if (!file.isEmpty()) {
            // Bild als Byte-Array setzen
            note.setImage(file.getBytes());
            log.info("Bild empfangen: " + file.getOriginalFilename());
        } else {
            log.error("Kein Bild hochgeladen!");
        }

        // Speichert der Notiz
        noteSvc.saveNote(note, session, categoryId);

        // Weiterleitung zur Dashboard-html
        return "dashboard";
    }

    /**
     * Ruft ein gespeichertes Bild einer Notiz durch die Notiz-ID ab.
     *
     * @param id ID der Notiz, des geforderten Bilds
     * @return ResponseEntity mit dem Bild als Byte-Array
     * @throws IOException Falls ein Fehler beim Laden des Bildes auftritt
     */
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        Optional<Notes> note = noteRepo.findById(id);
        // Überprüft, ob die Notiz existiert und ein Bild enthält
        if (note.isPresent() && note.get().getImage() != null) {
            String contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(note.get().getImage()));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(note.get().getImage());
        }
        // Überprüfen, ob die Notiz existiert und ein Bild enthält
        return ResponseEntity.notFound().build();
    }

    /**
     * Die Methode filtert Notizen anhand verschiedener optionaler Parameter wie Tags, Typ, Kategorie und Erstellungsdatum.
     *
     * @param tag Tag zur Filterung der Notizen
     * @param type Notiztyp zur Filterung
     * @param categoryId ID der Kategorie zur Filterung
     * @param createdAt Startdatum für die Filterung
     * @param toDate Enddatum für die Filterung
     * @param session HTTP-Session, um dwa angemeldeten Benutzers
     * @return ResponseEntity mit der Liste der gefilterten Notizen
     * @throws DataNotFoundException Falls eine benötigte Datenressource nicht gefunden wird
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Notes>> filterNotes(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) NoteType type,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate createdAt,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate toDate,
            HttpSession session) throws DataNotFoundException {

        // Überprüfen, welcher Benutzer eingeloggt ist
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // Benutzer ist nicht eingeloggt
        }

        // Kategorie abrufen, falls ID übergeben wurde
        Category category = null;
        if (categoryId != null) {
            category = categoryService.getCategoryById(categoryId);
        }

        // Loggen die Filterparameter
        log.debug("Filtern mit den folgenden Parametern - tag: {}, type: {}, category: {}, startDate: {}, endDate: {}",
                tag, type, categoryId, createdAt, toDate);

        //Notizen anhand der FIlterkriterien finden
        List<Notes> filteredNotes = noteSvcImpl.filterNotes(user, tag, type, categoryId, createdAt, toDate);
        if (filteredNotes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Keine Notizen gefunden
        }

        //Ausgabe der gefilterten Notizen
        return ResponseEntity.ok(filteredNotes);
    }


}
