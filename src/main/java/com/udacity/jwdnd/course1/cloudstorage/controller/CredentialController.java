package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private CredentialService credentialService;
    private UserService userService;
    private EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/add")
    public String createOrUpdateCredential(Credential credential, Authentication authentication, RedirectAttributes redirectAttributes){
        if (credential.getCredentialId() != null){
            try {
                credentialService.updateCredential(credential);
                redirectAttributes.addFlashAttribute("successMessage", "Your credential was successfully updated.");
                return "redirect:/home/result";
            }catch (Exception e) {
                System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the credential update. Please try again!");
                return "redirect:/home/result";
            }
            }else{
            try {
                String username = authentication.getName();
                int userId = userService.getUserId(username);
                credential.setUserId(userId);
                System.out.println("userId set:" + credential.getUserId());
                credential.setCredentialKey(generatePrivateKey());
                System.out.println("key set:" + credential.getCredentialKey());

                credentialService.addCredential(credential);
                System.out.println("credential added");
                redirectAttributes.addFlashAttribute("successMessage", "Your credential was successfully added.");
                System.out.println("credential url set:" + credential.getCredentialUrl());

                return "redirect:/home/result";
            }catch (Exception e){
                System.out.println("Cause: " + e.getCause() + ". Message: " + e.getMessage());
                redirectAttributes.addFlashAttribute("errorMessage", "Something went wrong with the credential upload. Please try again!");
                return "redirect:/home/result";
            }
        }
        //Use privately generated key
//        String credentialKey = generatePrivateKey();
//        credential.setCredentialKey(credentialKey);
//        encryptionService.encryptValue(credential.getCredentialPassword(), credentialKey)
    }


    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable(name = "credentialId") int credentialId, RedirectAttributes redirectAttributes){
        credentialService.deleteCredential(credentialId);
        redirectAttributes.addFlashAttribute("successMessage", "Your credential was successfully deleted.");
        return "redirect:/home/result";
    }


    private String generatePrivateKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
