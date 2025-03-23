package de.thowl.prog3.exam.web.gui;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        // Session ungültig machen
        session.invalidate();

        // Weiterleitung zur Login-Seite
        return "redirect:/login";
    }

}
