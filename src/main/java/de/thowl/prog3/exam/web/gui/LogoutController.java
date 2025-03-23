package de.thowl.prog3.exam.web.gui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller für das Abmelden.
 * Diese Klasse invalidiert die Session und meldet den Benutzer dadurch ab.
 * Benutzer wird anschließend auf Login-Seite weitergeleitet.
 *
 * @author Celeste Holsteg, Monique Rausche
 * @version 23.03.2025
 */
@Controller
public class LogoutController {

    /**
     * Diese Methode behandelt das Abmelden des Nutzers.
     * Die aktuelle Sitzung wird beendet und der Nutzer wird zur Login-Seite weitergeleitet.
     *
     * @param session aktuelle HTTP-Session des Benutzers
     * @return Login-Template
     */
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Benutzersession wird ungültig gemacht
        session.invalidate();

        // Weiterleiten zur Login-Seite
        return "redirect:/login";
    }

}
