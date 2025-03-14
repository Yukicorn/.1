package de.thowl.prog3.exam.storage.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import de.thowl.prog3.exam.storage.entities.Notes;


public interface NotesRepository extends CrudRepository<Notes, Long>  {
    List<Notes> findByTagsContainingIgnoreCase(String tag);
}
