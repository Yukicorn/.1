package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.NotesService;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.repositories.NotesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/share")
public class ShareNoteController {

    private final NotesRepository notesRepository;

    @Autowired
    public ShareNoteController(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @GetMapping("/{shareableLink}")
    public String shareNote(@PathVariable String shareableLink, Model model) {
        // Suchen der Notiz anhand des Freigabelinks
        log.debug("SharableLink");
        Notes note = notesRepository.findByShareableLink(shareableLink);

        // Falls Notiz nicht existiert, auf Fehlerseite umleiten
        if (note == null) {
            return "error"; // Falls eine error.html existiert
        }

        // Notiz in das Model legen, damit es in Thymeleaf verfügbar ist
        model.addAttribute("note", note);

        // Thymeleaf-Template "sharedNote.html" rendern
        return "sharedNote";
    }
}


