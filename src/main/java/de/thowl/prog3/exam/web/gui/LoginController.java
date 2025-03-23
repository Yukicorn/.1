package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.impl.UserServiceImpl;
import de.thowl.prog3.exam.storage.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller für den Benutzer-Login.
 * Diese Klasse verarbeitet die Anmeldung des Benutzers und speichert eine
 * aktuelle Benutzersession nach erfolgreicher Authentifizierung.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Slf4j
@Controller
public class LoginController {

    @Autowired
    UserServiceImpl svc;

    /**
     * Diese Methode zeigt das Login-Formular an.
     *
     * @return Template "loginform", das das Anmeldeformular enthält.
     */
    @GetMapping("/login")
    public String loginForm(){
        log.debug("entering loginForm");
        //gibt Login-Formular zurück
        return "loginform";
    }

    /**
     * Diese Methode verarbeitet den Login und authentifiziert den Benutzer.
     * Falls die Authentifizierung erfolgreich ist, wird der Benutzer in der Sitzung gespeichert.
     *
     * @param username Benutzername, der vom Benutzer eingegeben wurde
     * @param password Passwort, das vom Benutzer eingegeben wurde
     * @param session HttpSession-Objekt, die Benutzer in der Session speichert
     * @return Weiterleitung zum Dashboard bei Erfolg, sonst zurück zur Login-Seite
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // authentifiziert den Benutzer
        User user = svc.authenticate(username, password);
        if (user != null) {
            session.setAttribute("user", user); // Benutzer in Session speichern
            log.debug("Benutzer erfolgreich in Session gespeichert: "+user.getName());
            return "redirect:/dashboard"; //Weiterleiten zum Dashboard
        }
        return "login"; // Bei Fehler zurück zum Login
    }

}
