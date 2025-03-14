package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.thowl.prog3.exam.service.UserService;
import de.thowl.prog3.exam.web.gui.form.RegistrationForm;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RegistrationFormController {

    @Autowired
    @Qualifier("usermapper")
    private UserMapper mapper = new UserMapper();

    @Autowired
    UserServiceImpl svc;

    @GetMapping("/register")
    public String registerUserForm(){
        log.debug("entering registerUserForm");
        return "registerUser";
    }

    @PostMapping("/register")
    public String processRegistrationForm(Model model, RegistrationForm formdata){
        log.debug("entering processRegistrationForm");
        String username = formdata.getUsername();
        log.debug("Username ist: "+ username);
        String password = formdata.getPassword();
        log.debug("Passwort ist: "+password);
        String email = formdata.getEmail();
        log.debug("Email ist: "+email);

        //iwie übergabe an DB
        //String[] register = {username, password, email};
        //automatische id zuteilung siehe User?? gehört id zu aktiver sessionerkennung oder zu nutzererkennung in db?
        //log.debug(Arrays.toString(register));

        svc.registerUser(username, password, email); // Speichert den Benutzer in der DB

        return "userform";
    }

}
