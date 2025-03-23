package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.service.impl.NotesServiceImpl;
import de.thowl.prog3.exam.storage.entities.NoteType;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import de.thowl.prog3.exam.storage.entities.Category;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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
    public String showDashboard(@RequestParam(required = false) String tags,
                                @RequestParam(required = false) NoteType type,
                                @RequestParam(required = false) Long categoryId,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdAt,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                Model model, HttpSession session) throws DataNotFoundException {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new DataNotFoundException("Kein Benutzer in der Session gefunden.");
        }

        // Filtermethoden mit den Parametern aufrufen
        List<Notes> notesList = noteSvcImpl.filterNotes(user, tags, type, categoryId, createdAt, toDate);

        model.addAttribute("notesList", notesList);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "dashboard";
    }

}
