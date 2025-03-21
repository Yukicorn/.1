package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.service.impl.NotesServiceImpl;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import de.thowl.prog3.exam.storage.entities.Category;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Controller
public class Dashboard {

    @Autowired
    CategoryService categoryService;

    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();

    @Autowired
    private final NotesServiceImpl noteSvcImpl;

    public Dashboard(NotesServiceImpl noteSvcImpl){
        this.noteSvcImpl = noteSvcImpl;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) throws DataNotFoundException {
        List<Notes> notesList = noteSvcImpl.getAllNotesForCurrentUser(session);  // Alle Notizen abfragen
        model.addAttribute("notesList", notesList);  // Die Notizen an das Model übergeben
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "dashboard";  // Rückgabe des View-Namens (HTML-Seite für das Dashboard)
    }

}
