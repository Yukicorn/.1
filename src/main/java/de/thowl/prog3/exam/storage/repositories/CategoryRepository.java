package de.thowl.prog3.exam.storage.repositories;

import de.thowl.prog3.exam.storage.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Dieses Repository stellt eine Schnittstelle zur Datenbank für die Verwaltung von Kategorien bereit.
 *
 * @Author Monique Rausche, Celeste Holsteg
 * @version 21.03.2025
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Sucht eine Kategorie anhand ihres Namens.
     *
     * @param name Name der gesuchten Kategorie.
     * @return Optional-Objekt, das die Kategorie enthält, falls sie gefunden wurde.
     */
    Optional<Category> findByName(String name);

    /**
     * Sucht eine Kategorie anhand ihrer ID.
     *
     * @param id ID der gesuchten Kategorie.
     * @return Optional-Objekt, das die Kategorie enthält, falls sie gefunden wurde.
     */
    Optional<Category> findByid(Long id);
}