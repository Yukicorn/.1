package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import jakarta.servlet.http.HttpSession;

public interface NotesService {

    public Notes saveNote(Notes note, HttpSession session, Long categoryId) throws DataNotFoundException;
}
