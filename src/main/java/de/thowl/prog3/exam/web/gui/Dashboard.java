package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.impl.NotesServiceImpl;
import de.thowl.prog3.exam.service.impl.UserServiceImpl;
import de.thowl.prog3.exam.storage.entities.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Controller
public class Dashboard {

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();

    @Autowired
    private final NotesServiceImpl noteSvcImpl;

    public Dashboard(NotesServiceImpl noteSvcImpl){
        this.noteSvcImpl = noteSvcImpl;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Notes> notesList = noteSvcImpl.getAllNotes();  // Alle Notizen abfragen
        model.addAttribute("notesList", notesList);  // Die Notizen an das Model übergeben
        return "dashboard";  // Rückgabe des View-Namens (HTML-Seite für das Dashboard)
    }

}
