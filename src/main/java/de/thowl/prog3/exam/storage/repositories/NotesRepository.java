package de.thowl.prog3.exam.storage.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.NoteType;
import de.thowl.prog3.exam.storage.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import de.thowl.prog3.exam.storage.entities.Notes;
import org.springframework.data.repository.query.Param;

/**
 * Repository-Interface für die Entität {@link Notes}.
 * Dieses Repository stellt eine Schnittstelle für die verwaltung von Notizen bereit
 *
 * @author Monique Rausche, Celeste Holsteg
 * @version 21.03.2025
 */
public interface NotesRepository extends CrudRepository<Notes, Long> {

    /**
     * Sucht Notizen, deren Tags den gesuchten Tag enthalten (unabhängig von Groß-/Kleinschreibung).
     *
     * @param tag Der zu suchende Tag
     * @return Liste von Notizen mit übereinstimmenden Tags.
     */
    List<Notes> findByTagsContainingIgnoreCase(String tag);

    /**
     * Sucht eine Notiz anhand ihrer ID.
     *
     * @param id ID der gesuchten Notiz.
     * @return gefundene Notiz falls sie existiert
     */
    @Override
    Optional<Notes> findById(Long id);

    /**
     * Filtert Notizen anhand mehrerer Kriterien: Benutzer, Tags, Typ, Kategorie, Erstellungsdatum und Enddatum.
     *
     * @param user      Benutzer, dem die Notizen gehören.
     * @param tags      Tags zur Filterung der Notizen.
     * @param type      Der Typ der Notiz.
     * @param category  Die Kategorie der Notiz.
     * @param createdAt Startdatum für die Filterung.
     * @param toDate    Enddatum für die Filterung.
     * @return Eine Liste von Notizen, die den Filterkriterien entsprechen.
     */
    @Query("SELECT n FROM Notes n WHERE " +
            "n.user = :user AND " +
            "(:tags IS NULL OR LOWER(n.tags) LIKE LOWER(CONCAT('%', :tags, '%'))) AND " +
            "(:type IS NULL OR n.type = :type) AND " +
            "(:category IS NULL OR n.category = :category) AND " +
            "(:createdAt IS NULL OR n.createdAt >= :createdAt) AND " +
            "(:toDate IS NULL OR n.createdAt <= :toDate)")
    List<Notes> filterNotes(
            @Param("user") User user,
            @Param("tags") String tags,
            @Param("type") NoteType type,
            @Param("category") Category category,
            @Param("createdAt") LocalDate createdAt,
            @Param("toDate") LocalDate toDate
    );

    /**
     * Sucht alle Notizen, die einem bestimmten Benutzer gehören.
     *
     * @param user Benutzer, dessen Notizen gesucht werden.
     * @return Liste von Notizen, die dem Benutzer gehören.
     */
    List<Notes> findByUser(User user);

    /**
     * Sucht eine Notiz anhand ihres Freigabelinks.
     *
     * @param shareableLink eindeutiger Freigabelink.
     * @return gefundene Notiz oder null, falls keine vorhanden ist.
     */
    Notes findByShareableLink(String shareableLink);
}