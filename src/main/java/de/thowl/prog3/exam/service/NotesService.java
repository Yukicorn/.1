package de.thowl.prog3.exam.service;

import de.thowl.prog3.exam.storage.entities.Category;
import de.thowl.prog3.exam.storage.entities.Notes;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

public interface NotesService {

    //public Notes saveNote(Notes note, String username) throws DataNotFoundException;

    public Notes saveNote(Notes note, HttpSession session, Long categoryId) throws DataNotFoundException;
}
