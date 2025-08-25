package com.example.enotes.endpoint;

import com.example.enotes.dto.LoginRequest;
import com.example.enotes.dto.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception;

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;

}
