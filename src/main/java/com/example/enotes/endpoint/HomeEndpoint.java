package com.example.enotes.endpoint;

import com.example.enotes.dto.PasswordResetRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Home", description = "Home (Utility) related API's")
@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

    @Operation(summary = "Verify user account endpoint")
    @GetMapping("/verify")
    ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception;

    @Operation(summary = "Send user an email for password reset endpoint")
    @GetMapping("/send-email-reset")
    ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception;

    @Operation(summary = "Link verify user password reset endpoint")
    @GetMapping("/verify-password-link")
    ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception;

    @Operation(summary = "Reset user password endpoint")
    @PostMapping("/reset-password")
    ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception;

}
