package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHome(Authentication authentication, Model model, Note note, Credential credential){
        System.out.println("GetHome");
        String username = authentication.getName();
        int userId = userService.getUserId(username);
        List<File> files = fileService.getAllFilesForUser(userId);
        List<Note> notes = noteService.getAllNotesForUser(userId);
        List<Credential> credentials = credentialService.getAllCredentialsForUser(userId);
        model.addAttribute("notes",notes);
        model.addAttribute("files",files);
        model.addAttribute("credentials",credentials);
        return "home";}

    @GetMapping("/result")
    public String getResult() {
        System.out.println("GetResult");
        return "result";
    }
}

