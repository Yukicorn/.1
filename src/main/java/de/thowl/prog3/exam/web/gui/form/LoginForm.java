package de.thowl.prog3.exam.web.gui.form;

import lombok.Data;

/**
 * Datenmodell für das Loginformular.
 * Diese Klasse dient als Datencontainer für die Eingaben aus dem Loginformular.
 * Sie wird verwendet, um Benutzername und Passwort zu speichern und an den Controller zu übergeben.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Data
public class LoginForm {
    //Benutzername, der im Formular eingegeben wird.
    public String username;
    //Passwort, das im Formular eingegeben wird.
    public String password;
}
