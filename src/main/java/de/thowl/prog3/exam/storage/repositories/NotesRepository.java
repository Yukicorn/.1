package de.thowl.prog3.exam.storage.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.NoteType;
import de.thowl.prog3.exam.storage.entities.User;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import de.thowl.prog3.exam.storage.entities.Notes;
import org.springframework.data.repository.query.Param;


public interface NotesRepository extends CrudRepository<Notes, Long>  {
    List<Notes> findByTagsContainingIgnoreCase(String tag);
    @Override
    Optional<Notes> findById(Long id);

    @Query("SELECT n FROM Notes n WHERE " +
            "n.user = :user AND " +  // Benutzerfilter hinzufügen
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

    List<Notes> findByUser(User user);

    Optional<Notes> findByShareableLink(String shareableLink);
}

