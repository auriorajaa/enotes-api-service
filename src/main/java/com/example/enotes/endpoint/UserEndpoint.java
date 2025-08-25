package com.example.enotes.endpoint;

import com.example.enotes.dto.PasswordChangeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User", description = "User related API's")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

    @Operation(summary = "User profile endpoint")
    @GetMapping("/profile")
    ResponseEntity<?> getProfile();

    @Operation(summary = "User change password endpoint")
    @PostMapping("/change-password")
    ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest);

}
