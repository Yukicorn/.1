package de.thowl.prog3.exam.web.gui.form;

import jakarta.servlet.http.HttpSession;
import lombok.Data;

@Data
public class CreateNoteForm {
    public String titel;
    public HttpSession session;
    public String tags;
    public String content;
    public byte[] image;
}
