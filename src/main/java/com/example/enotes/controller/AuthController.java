package com.example.enotes.controller;

import com.example.enotes.dto.LoginRequest;
import com.example.enotes.dto.LoginResponse;
import com.example.enotes.dto.UserRequest;
import com.example.enotes.service.UserService;
import com.example.enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception {
       String url = CommonUtil.getUrl(request);

        Boolean register = userService.register(userRequest, url);

        if (register) {
            return CommonUtil.createBuildResponseMessage("User registered successfully", HttpStatus.CREATED);
        }

        return CommonUtil.createErrorResponseMessage("User registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = userService.login(loginRequest);

        if (ObjectUtils.isEmpty(loginResponse)) {
            return CommonUtil.createErrorResponseMessage("Invalid credentials. Login failed", HttpStatus.BAD_REQUEST);
        }

        return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
    }
}
