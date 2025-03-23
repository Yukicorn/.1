package de.thowl.prog3.exam.web.gui.form;

import jakarta.servlet.http.HttpSession;
import lombok.Data;

/**
 * Datenmodell für das Erstellen einer neuen Notiz.
 * Diese Klasse dient als Datencontainer für die Eingaben aus dem CreateNote-Formular.
 * Diese sind Titel, Inhalt, Tags und eines optionalen Bildes.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Data
public class CreateNoteForm {
    //Titel, der im Formular eingegeben wird.
    public String titel;
    //aktuelle Benutzersession des angemeldeten Nutzers
    public HttpSession session;
    //Tags, die im Formular eingegeben werden.
    public String tags;
    //Inhalt der Notiz, der im Formular eingegeben wird.
    public String content;
    //Bild, das optional hochgeladen wird.
    public byte[] image;
}
