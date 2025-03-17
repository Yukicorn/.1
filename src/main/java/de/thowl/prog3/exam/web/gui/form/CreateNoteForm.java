package de.thowl.prog3.exam.web.gui.form;

import de.thowl.prog3.exam.storage.entities.Notes;
import jakarta.servlet.http.HttpSession;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateNoteForm {
    public String titel;
    public HttpSession session;
    public String tags;
    public String content;
}
