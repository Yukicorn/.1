package de.thowl.prog3.exam.web.gui.form;

import lombok.Data;

/**
 * Datenmodell für das Benutzerformular.
 * Diese Klasse dient als Datencontainer für die Eingaben aus dem Benutzerformular.
 * Sie wird verwendet, um den Benutzernamen zu speichern und an den Controller zu übergeben.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Data
public class UserForm {
    //Benutzername, der im Formular eingegeben wird
    public String username;
}
