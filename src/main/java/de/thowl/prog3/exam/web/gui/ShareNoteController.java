package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.repositories.NotesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller für das Teilen einer Notiz
 * Diese Klasse ermöglicht das Aufrufen einer Notiz über einen FreigabeLink und
 * das Abrufen dieser Notiz, ohne dass der Benutzer eingeloggt sein muss.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */

@Slf4j
@Controller
@RequestMapping("/share")//Basis-Url für Endpunkte dieses Controllers
public class ShareNoteController {

    @Autowired
    private final NotesRepository notesRepository;//Zugriff auf Notizen über Repository

    /**
     * Konstruktor für den ShareNoteController, initialisiert eine Instanz des NotesRepository
     *
     * @param notesRepository  Repository für den Zugriff auf gespeicherte Notizen
     */
    public ShareNoteController(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    /**
     * Diese Methode sucht eine Notiz anhand eines Freigabelinks.
     * Falls die Notiz existiert, wird sie über ein HTML-File ausgegeben
     *
     * @param shareableLink Freigabelink zur Identifikation der Notiz
     * @param model Verwendetes Model im Thymeleaf-Template
     * @return Template "sharedNote", das die Notiz anzeigt
     */
    @GetMapping("/{shareableLink}")
    public String shareNote(@PathVariable String shareableLink, Model model) {
        log.debug("SharableLink");
        // sucht Notiz anhand des Freigabelinks
        Notes note = notesRepository.findByShareableLink(shareableLink);

        // Notiz für Thymeleaf verfügbar machen
        model.addAttribute("note", note);

        // Weiterleiten auf sharedNote.html
        return "sharedNote";
    }
}


