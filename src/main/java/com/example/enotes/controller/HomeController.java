package com.example.enotes.controller;

import com.example.enotes.dto.PasswordResetRequest;
import com.example.enotes.service.HomeService;
import com.example.enotes.service.UserService;
import com.example.enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception {
        Boolean verifyAccount = homeService.verifyAccount(uid, code);

        if (verifyAccount) return CommonUtil.createBuildResponseMessage("Account verified successfully", HttpStatus.OK);

        return  CommonUtil.createBuildResponseMessage("Account verification failed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/send-email-reset")
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception {
        userService.sendEmailPasswordReset(email, request);

        return CommonUtil.createBuildResponseMessage("Email sent successfully. Please check your email for further information", HttpStatus.OK);
    }

    @GetMapping("/verify-password-link")
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception {
        userService.verifyPasswordResetLink(uid, code);

        return CommonUtil.createBuildResponseMessage("Link verified successfully", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception {
        userService.resetPassword(passwordResetRequest);

        return CommonUtil.createBuildResponseMessage("Password reset successfully", HttpStatus.OK);
    }

}
