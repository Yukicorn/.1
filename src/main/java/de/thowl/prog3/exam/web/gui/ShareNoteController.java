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

@Slf4j
@Controller
@RequestMapping("/share")
public class ShareNoteController {

    @Autowired
    private final NotesRepository notesRepository;//Zugriff auf Notizen über Repository

    public ShareNoteController(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

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


