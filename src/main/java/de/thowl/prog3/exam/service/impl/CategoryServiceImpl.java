package de.thowl.prog3.exam.service.impl;

import de.thowl.prog3.exam.service.CategoryService;
import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.thowl.prog3.exam.storage.repositories.CategoryRepository;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Kategorien.
 * Diese Klasse enthält Methoden Abrufen und Erstellen von Kategorien.
 * Sie implementiert die Klasse CategoryService.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    /**
     * Konstruktor für CategoryServiceImpl, der das CategoryRepository initialisiert.
     *
     * @param categoryRepository Repository für den Zugriff auf Kategorien in der Datenbank
     */
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository){//wird Methode gebraucht?
        this.categoryRepository = categoryRepository;
    }

    /**
     * Diese Methode gibt alle Kategorien aus der Datenbank zurück.
     *
     * @return Liste aller Kategorien
     */
    @Override
    public List<Category> getAllCategories() {
        //Gibt alle Kategorien aus der Datenbank zurück
        return categoryRepository.findAll();
    }

    /**
     * Diese Methode gibt eine Kategorie anhand ihrer ID zurück.
     * Falls die Kategorie nicht gefunden wird, wird eine DataNotFoundException geworfen.
     *
     * @param categoryId ID der Kategorie, die abgerufen werden soll
     * @return Kategorie mit der angegebenen ID
     * @throws DataNotFoundException Falls die Kategorie mit der angegebenen ID nicht gefunden wird
     */
    @Override
    public Category getCategoryById(Long categoryId) throws DataNotFoundException {
        if (categoryId == null) {
            throw new IllegalArgumentException("Category ID darf nicht null sein!");
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Kategorie nicht gefunden"));
    }

    /**
     * Diese Methode erstellt eine neue Kategorie und speichert sie in der Datenbank.
     *
     * @param name Name der neuen Kategorie
     * @return erstellte Kategorie
     */
    @Override
    public Category createCategory(String name) {
        // Erstellt eine neue Kategorie und speichert sie in der Datenbank
        Category category = new Category();
        category.setName(name);
        //speichert die neue Kategorie und gibt sie zurück
        return categoryRepository.save(category);
    }

}
