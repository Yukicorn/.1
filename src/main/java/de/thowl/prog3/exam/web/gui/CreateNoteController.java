package de.thowl.prog3.exam.web.gui;

import de.thowl.prog3.exam.service.impl.NoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.thowl.prog3.exam.web.gui.form.CreateNoteForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CreateNoteController {

    @Autowired
    NoteServiceImpl svc;

    @GetMapping("/createNote")
    public String createNoteForm(){
        log.debug("entering createNoteForm");
        return "createNote";//erstellen name etc und dann neue seite um inhalt zu bearbeiten
    }

    @PostMapping("/createNote")
    public String createNewNote(Model model, CreateNoteForm formdata){
        log.debug("entering createNewNoteForm");
        String noteTitel = formdata.getNoteTitel();

        svc.saveNote(??????????)

        return "noteOverwiew";
    }

}
