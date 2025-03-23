package de.thowl.prog3.exam.web.gui.form;

import lombok.Data;

/**
 * Datenmodell für das Registrierungsformular.
 * Diese Klasse dient als Datencontainer für die Eingaben aus dem Registrierungsformular.
 * Sie wird verwendet, um Benutzername, Passwort und Email zu speichern und an den Controller zu übergeben.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Data
public class RegistrationForm {
    //Benutzername, der im Formular eingegeben wird.
    public String username;
    //Passwort, das im Formular eingegeben wird.
    public String password;
    //E-Mail, die im Formular eingegeben wird.
    public String email;
}
