package de.thowl.prog3.exam.service.impl;


import de.thowl.prog3.exam.storage.repositories.UserRepository;
import de.thowl.prog3.exam.storage.entities.User;
import de.thowl.prog3.exam.web.api.DataNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteServiceImpl(NoteRepository noteRepository, UserRepository userRepository){
        this.noteRepository=noteRepository;
        this.userRepository=userRepository;
    }

    public Note saveNote(Note note, String username){
        //finde aktuell authentifizierten Nutzer
        User user = userRepository.findUserByName(username)
                .orElseThrow(()-> new DataNotFoundException("Nutzer nicht vorhanden."));
        note.setUser(user);
        return noteRepository.save(note);
    }

}
