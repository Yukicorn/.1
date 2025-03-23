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

@Slf4j
@Controller
public class LoginController {

    @Autowired
    UserServiceImpl svc;

    @GetMapping("/login")
    public String loginForm(){
        log.debug("entering loginForm");
        return "loginform";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        // Authentifiziere den Benutzer (z.B. über UserService)
        User user = svc.authenticate(username, password);
        if (user != null) {
            session.setAttribute("user", user); // Benutzer in Session speichern
            log.debug("Benutzer erfolgreich in Session gespeichert: "+user.getName());
            return "redirect:/dashboard";
        }
        return "login"; // Bei Fehler zurück zum Login
    }

}
