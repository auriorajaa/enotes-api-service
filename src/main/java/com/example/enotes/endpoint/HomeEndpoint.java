package com.example.enotes.endpoint;

import com.example.enotes.dto.PasswordResetRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

    @GetMapping("/verify")
    ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception;

    @GetMapping("/send-email-reset")
    ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception;

    @GetMapping("/verify-password-link")
    ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception;

    @PostMapping("/reset-password")
    ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception;

}
