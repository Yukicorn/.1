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

/**
 * Controller für das Dashboard, das die Benutzeroberfläche zur Anzeige und Filterung von Notizen bereitstellt.
 * Diese Klasse verwaltet die Anzeige von Notizen und stellt Filterung nach Tags, Typ, Kategorie und Datum bereit.
 * @Author Celeste Holsteg, Monique Rausche
 * @version 21.03.2024
 */

@Slf4j
@Controller
public class Dashboard {
    // Services für Kategorien
    @Autowired
    CategoryService categoryService;
    // Mapper für Benutzerinformationen
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();
    // Service zum Verwalten von Notizen
    @Autowired
    private final NotesServiceImpl noteSvcImpl;
    /**
     * Konstruktor, Initialisierung des NotesService.
     *
     * @param noteSvcImpl Service, der für die Verwaltung von Notizen verantwortlich ist.
     */
    public Dashboard(NotesServiceImpl noteSvcImpl){
        this.noteSvcImpl = noteSvcImpl;
    }
    /**
     * Zeigt das Dashboard an, das eine Liste von Notizen basierend auf den gewünschten Filtern enthält.
     * Diese Methode ruft die Notizen vom Service ab, filtert sie und stellt sie im Modell für das Dashboard bereit.
     *
     * @param tags Filter nach Tags der Notizen.
     * @param type Filter nach Typ der Notizen
     * @param categoryId Filter nach Kategorie-ID der Notizen.
     * @param createdAt Filter nach Erstellungsdatum der Notizen.
     * @param toDate Enddatum, bis zu dem Notizen angezeigt werden sollen.
     * @param model Modell, das die Notizen und Kategorien an das Thymeleaf-Template übergibt.
     * @param session HTTP-Session, die den aktuell authentifizierten Benutzer enthält.
     * @return Thymeleaf-Templates, das das Dashboard anzeigt
     * @throws DataNotFoundException Wenn kein Benutzer in der Session gefunden wird.
     */
    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(required = false) String tags,
                                @RequestParam(required = false) NoteType type,
                                @RequestParam(required = false) Long categoryId,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdAt,
                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                Model model, HttpSession session) throws DataNotFoundException {
        // Holt den Benutzer aus der Session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new DataNotFoundException("Kein Benutzer in der Session gefunden.");
        }

        // Filtermethoden mit den Parametern aufrufen
        List<Notes> notesList = noteSvcImpl.filterNotes(user, tags, type, categoryId, createdAt, toDate);
        // Fügt die gefilterten Notizen und die verfügbaren Kategorien dem Modell hinzu
        model.addAttribute("notesList", notesList);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "dashboard";         // Gibt das Thymeleaf-Template für das Dashboard zurück
    }

}
