package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    @PostConstruct
    public void postConstruct(){System.out.println("Creating NoteService bean");}

    public int addNote(Note note){
        return noteMapper.addNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));
    }

    public List<Note> getAllNotesForUser(int userId){
        return noteMapper.getAllNotesForUser(userId);
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note);
    }

    public void deleteNote(int noteId) {
        System.out.println("Note Service Deleting Note");
        noteMapper.deleteNote(noteId);
    }
}
