package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile, Model model) {
        System.out.println("Starting addFile()");
        String errorMessage = null;
        File file = null;

        String username = authentication.getName();
        System.out.println("username:" + username);
        int userId = userService.getUserId(username);
        System.out.println("userId:"+ userId);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        System.out.println("fileName:" + fileName);

        if (!fileService.checkForDuplicates(userId, fileName).isEmpty()) {
            System.out.println("Duplicate exists");
            errorMessage = "File name already exists.";
        }

        if (errorMessage == null) {
            System.out.println("No duplicates");
            file = new File();
            file.setFileName(fileName);
            file.setContentType(multipartFile.getContentType());
            file.setFileSize(Long.toString(multipartFile.getSize()));
            file.setUserId(userId);
            try {
                file.setFileData(multipartFile.getBytes());
                fileService.uploadFile(file);
                System.out.println("file uploaded");
                return "redirect:/result?success";
            } catch (IOException e) {
                errorMessage = "Error uploading file.";
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "result";
    }

    @GetMapping("/view/{fileId}")
    public ResponseEntity downloadFile(@PathVariable(name = "fileId") String fileId){
        File file = fileService.downloadFile(Integer.parseInt(fileId));
        System.out.println("File downloaded");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(file.getFileData());
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable(name = "fileId") Integer fileId){
        fileService.deleteFile(fileId);
        return "redirect:/result?success";
    }

}
