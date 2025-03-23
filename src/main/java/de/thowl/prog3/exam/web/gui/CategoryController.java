package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import de.thowl.prog3.exam.storage.entities.Category;

import java.util.List;

/**
 * Diese Klasse ist ein REST-Controller zur Verwaltung von Kategorien. Sie bietet Endpunkte zum Abrufen aller Kategorien, Erstellen einer neuen Kategorie
 * sowie zum Anzeigen des Formulars zur Erstellung einer Notiz.
 * @author Celeste Holsteg, Monique Rausche
 * @version 21.03.2025
 */

@RestController
@RequestMapping("/api/categories") // Basispfad für alle API-Endpunkte dieser Klasse
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Konstruktor für den CategoryController, wird mit dem CategoryService initialisiert, um auf die Kategorien-Logik zugreifen zu können.
     *
     * @param categoryService Service, der die Kategorien verwaltet.
     */

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Holt alle Kategorien und gibt sie zurück.
     * @return Eine ResponseEntity mit einer Liste aller Kategorien.
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        // Ruft alle Kategorien ab und gibt sie als JSON zurück
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * Erstellt eine neue Kategorie mit dem angegebenen Namen.
     * @param name Der Name der neuen Kategorie.
     * @return Eine ResponseEntity mit der neu erstellten Kategorie.
     */
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestParam String name) {
        // Erstellt eine neue Kategorie und gibt sie zurück
        Category createdCategory = categoryService.createCategory(name);
        return ResponseEntity.ok(createdCategory);
    }
    /**
     * Zeigt das Formular zur Erstellung einer neuen Notiz an und fügt die Kategorien ein.
     * @param model Verwendetes Model im Thymeleaf-Template
     * @return Template zur Notizerstellung
     */
    @GetMapping("/createNote")
    public String createNoteForm(Model model) {
        // Holt alle Kategorien und fügt sie dem Model hinzu, damit sie im Template angezeigt werden
        model.addAttribute("categories", categoryService.getAllCategories());
        return "createNote"; // gibt das createNote Template zurück
    }

}
