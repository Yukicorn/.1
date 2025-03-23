package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.web.api.DataNotFoundException;

import java.util.List;

/**
 * Service-Schnittstelle für Verwaltung der Kategorien.
 * Diese Schnittstelle definiert Methoden für das Erstellen und Abrufen von Kategorien.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
public interface CategoryService {

    /**
     * Die Methode holt alle Kategorien aus der Datenbank.
     *
     * @return Liste aller Kategorien
     */
    List<Category> getAllCategories();

    /**
     * Die Methode erstellt eine neue Kategorie mit dem angegebenen Namen.
     *
     * @param name Name der neuen Kategorie
     * @return erstellte Kategorie
     */
    Category createCategory(String name);

    /**
     * Die Methode holt eine Kategorie anhand ihrer ID.
     *
     * @param id ID der gesuchten Kategorie
     * @return gefundene Kategorie
     * @throws DataNotFoundException Wenn keine Kategorie mit der angegebenen ID gefunden wird
     */
    Category getCategoryById(Long id) throws DataNotFoundException;

}
