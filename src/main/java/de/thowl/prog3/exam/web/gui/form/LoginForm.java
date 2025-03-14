package de.thowl.prog3.exam.web.gui.form;

import lombok.Data;

@Data
public class LoginForm {
    //username oder Email? kann man nutzer eins zur auswahl stellen? entweder oder angeben?
    public String username;
    public String password;
}
