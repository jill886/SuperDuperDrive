package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String addFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multipartFile, RedirectAttributes redirectAttributes) {
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
            errorMessage = "File name already exists. Please choose another file to upload.";
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
                redirectAttributes.addFlashAttribute("successMessage", "Your file was successfully uploaded.");
                return "redirect:/home/result";
            } catch (IOException e) {
                errorMessage = "Error uploading file.";
            }
        }
        redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        return "redirect:/home/result";
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
    public String deleteFile(@PathVariable(name = "fileId") Integer fileId, RedirectAttributes redirectAttributes){
        fileService.deleteFile(fileId);
        redirectAttributes.addFlashAttribute("successMessage", "Your file was successfully deleted.");
        return "redirect:/home/result";
    }

}
