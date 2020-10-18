package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/note")
public class NoteController {

//    private Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final UserService userService;
    private final NoteService noteService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @PostMapping("/add")
    public String createOrUpdateNote(Authentication authentication, Note note, RedirectAttributes redirectAttributes) {

        System.out.println("createOrUpdateNote");
        String username = authentication.getName();
        System.out.println("username:"+ username);
        int userId = userService.getUserId(username);
        System.out.println("userId:" + userId);
        note.setUserId(userId);
        System.out.println("note.userId:" + note.getUserId());


        if (note.getNoteId() != null) {
            System.out.println("noteId exists");
            try{
                noteService.updateNote(note);
                redirectAttributes.addFlashAttribute("successMessage", "Your note was updated successful.");
                return "result";
            }catch (Exception e){
                System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note update. Please try again!");
                return "result";
            }
        }
        else{
            try{
                System.out.println("noteId does not exist");
                noteService.addNote(note);
                System.out.println("Line 62 in NoteController");
                redirectAttributes.addFlashAttribute("successMessage", "Your note was created successful.");
                System.out.println("Line 64 in NoteController");
                return "result";
            }catch (Exception e){
                System.out.println("Line 67 in NoteController");
                System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the note creation. Please try again!");
                return "result";
            }

        }
    }

    @GetMapping("/delete/{noteId}")
    public String deleteNote(@PathVariable int noteId, RedirectAttributes redirectAttributes) {
        try{
            noteService.deleteNote(noteId);
            redirectAttributes.addFlashAttribute("successMessage", "Your note was deleted successful.");
            return "redirect:/result";
        }catch (Exception e){
            System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong deleting the note. Please try again!");
            return "redirect:/result";
        }

    }
}