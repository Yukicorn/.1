package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.thowl.prog3.exam.web.gui.form.RegistrationForm;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller zum Registrieren eines Benutzers.
 * Diese Klasse steuert das Registrieren eines neuen Nutzers.
 * Nach erfolgreicher Registrierung wird der Benutzer zur Login-Seite weitergeleitet.
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Slf4j
@Controller
public class RegistrationFormController {

    @Autowired
    UserServiceImpl svc;

    /**
     * Diese Methode zeigt das Registrierungsformular an.
     * @return Thymeleaf-Template "registerUser", das das Registrierungsformular anzeigt
     */
    @GetMapping("/register")
    public String registerUserForm(){
        log.debug("entering registerUserForm");
        return "registerUser"; //rendert die Registrierungsseite
    }

    /**
     * Diese Methode verarbeitet das ausgefüllte Registrierungsformular.
     * Die Benutzerdaten werden gespeichert.
     * Der Benutzer wird zur Login-Seite weitergeleitet.
     *
     * @param model Verwendetes Model im Thymeleaf-Template
     * @param formdata Enthält die vom Benutzer eingegebenen Registrierungsdaten
     * @return Weiterleitung zur Login-Seite
     */
    @PostMapping("/register")
    public String processRegistrationForm(Model model, RegistrationForm formdata){
        log.debug("entering processRegistrationForm");
        ///holt die eingegebenen Daten aus dem Registrierungsformular
        String username = formdata.getUsername();
        log.debug("Username ist: "+ username);
        String password = formdata.getPassword();
        log.debug("Passwort ist: "+password);
        String email = formdata.getEmail();
        log.debug("Email ist: "+email);

        //speichert den Benutzer in der Datenbank
        svc.registerUser(username, password, email);

        // gibt das Login-Template zurück
        return "loginform";
    }

}
