package de.thowl.prog3.exam.web.gui.form;

import de.thowl.prog3.exam.storage.entities.Notes;
import jakarta.servlet.http.HttpSession;
import lombok.Data;

@Data
public class CreateNoteForm {
    public String titel;
    public HttpSession session;
}
