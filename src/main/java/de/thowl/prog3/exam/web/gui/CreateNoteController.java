package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.service.impl.NotesServiceImpl;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.storage.repositories.NotesRepository;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import de.thowl.prog3.exam.web.gui.form.CreateNoteForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Controller für das Erstellen von Notizen.
 * Diese Klasse steuert das Erstellen einer neuen Notiz und Hochladen von Bildern,
 * Ermöglicht es Benutzern, Notizen zu erstellen, zu kategorisieren und mit Tags zu versehen.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 21.03.2025
 */

@Slf4j
@Controller
public class CreateNoteController {

    @Autowired
    NotesServiceImpl svc;
    @Autowired
    UserService usvc;
    @Autowired
    CategoryService categoryService;

    /**
     * Diese Methode zeigt das Formular zum Erstellen einer neuen Notiz an, holt alle Kategorien aus der Datenbank und gibt sie an das Template weiter.
     * @param model  Model-Objekt, das an Thymeleaf-Template übergeben wird, um die Daten anzuzeigen.
     * @return Thymeleaf-Template "createNote", dass das Formular rendert.
     */
    @GetMapping("/createNote")
    public String createNoteForm(Model model) {
        // Holt alle Kategorien aus der Datenbank, um sie im Formular anzuzeigen.
        List<Category> categories = categoryService.getAllCategories();
        // Fügt die Kategorien dem Modell hinzu, damit sie im Template verfügbar sind.
        model.addAttribute("categories", categories);
        // Gibt das Thymeleaf-Template zurück, das das Formular zum Erstellen einer Notiz anzeigt.
        return "createNote"; // Thymeleaf Template
    }
    /**
     * Verarbeitet die Formulardaten zum Erstellen einer neuen Notiz.
     * Speichert die Notiz, wenn alle erforderlichen Daten validiert wurden.
     *
     * @param note Notiz-Objekt, das aus dem Formular entnommen wird.
     * @param formdata Formulardaten, die die Tags, den Titel und den Inhalt der Notiz enthalten.
     * @param session aktuelle HTTP-Session, um den angemeldeten Benutzer zu bestimmen.
     * @param file hochgeladene Bilddatei.
     * @param categoryId ID der ausgewählten Kategorie für die Notiz.
     * @return "dashboard"- Template, das nach erfolgreichem Speichern der Notiz angezeigt wird.
     * @throws DataNotFoundException Falls die Session oder der Benutzer nicht gefunden werden kann.
     * @throws IOException Falls ein Fehler beim Speichern des Bildes auftritt.
     */
    @PostMapping("/createNote")
    public String createNote(@ModelAttribute Notes note, CreateNoteForm formdata, HttpSession session, @RequestParam("imageFile")MultipartFile file, @RequestParam("categoryId") Long categoryId) throws DataNotFoundException, IOException {
        log.debug("processing createNoteForm");
// Überprüft, ob die Session null ist, was auf ein Problem mit der Benutzeranmeldung hinweist.
        if (session == null) {
            log.error("HttpSession ist NULL! Die Session wurde nicht korrekt verwaltet.");
            throw new DataNotFoundException("Session existiert nicht.");
        }

        User user= usvc.getUserFromSession(session);

        // Überprüft, ob der Benutzer null ist
        if (user == null) {
            log.error("Benutzer ist Null");
            throw new DataNotFoundException("Benutzer nicht in der Session gefunden.");  // Wirft eine Ausnahme, wenn der Benutzer nicht gefunden wird.
        }

        log.debug("Benutzer aus der Session: "+user.getName());
        // Holt den Titel der Notiz aus den Formulardaten und setzt ihn.
        String noteTitel = formdata.getTitel();
        //Notes note = new Notes();
        note.setTitle(noteTitel); // Setzt den Titel der Notiz.

        log.debug("Titel: " + noteTitel);
        //log.debug("User: " + user.getUser(user));
        // Holt die Tags der Notiz aus den Formulardaten und setzt sie.
        String notesTags = formdata.getTags();
        note.setTags(notesTags);
// Holt den Inhalt der Notiz aus den Formulardaten und setzt ihn.
        String notesContent = formdata.getContent();
        note.setContent(notesContent);
// Überprüft, ob eine Bilddatei hochgeladen wurde und setzt das Bild als Byte-Array
        if (!file.isEmpty()) {
            // Bild als Byte-Array setzen
            note.setImage(file.getBytes());
            log.info("Bild empfangen: " + file.getOriginalFilename());
        } else {
            log.error("Kein Bild hochgeladen!");
        }

        note.setUser(user); // Setze den Benutzer in die Notiz
        // Überprüft, ob eine Kategorie-ID aus dem Formular übergeben wurde. Wenn nicht, wird eine Ausnahme geworfen.
        if (categoryId == null) {
            log.error("Kategorie-ID ist NULL!");
            throw new IllegalArgumentException("Kategorie-ID darf nicht null sein!");
        }
// Speichert die Notiz durch Verwendung des NotesServiceImpl
        svc.saveNote(note, session, categoryId);
// Speichert die Notiz durch Verwendung des NotesServiceImpl
        return "dashboard";
    }

}
