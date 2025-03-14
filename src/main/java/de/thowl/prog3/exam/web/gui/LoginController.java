package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.thowl.prog3.exam.web.gui.form.LoginForm;
import de.thowl.prog3.exam.web.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;

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
    public String processLoginForm(Model model, LoginForm formdata){
        log.debug("entering loginForm");
        String username = formdata.getUsername();
        log.debug("Username ist: "+ username);
        String password = formdata.getPassword();
        log.debug("Passwort ist: "+ password);

        //user checken, ob vorhanden und ob Daten übereinstimmen

        return "homepage";
    }


    //how tf geht logout??

}
