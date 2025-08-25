package com.example.enotes.controller;

import com.example.enotes.dto.LoginRequest;
import com.example.enotes.dto.LoginResponse;
import com.example.enotes.dto.UserRequest;
import com.example.enotes.endpoint.AuthEndpoint;
import com.example.enotes.service.AuthService;
import com.example.enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController implements AuthEndpoint {

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<?> registerUser(UserRequest userRequest, HttpServletRequest request) throws Exception {
        String url = CommonUtil.getUrl(request);

        Boolean register = authService.register(userRequest, url);

        if (!register) {
            return CommonUtil.createErrorResponseMessage("User registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return CommonUtil.createBuildResponseMessage("User registered successfully", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) throws Exception {
        LoginResponse loginResponse = authService.login(loginRequest);

        if (ObjectUtils.isEmpty(loginResponse)) {
            return CommonUtil.createErrorResponseMessage("Invalid credentials. Login failed", HttpStatus.BAD_REQUEST);
        }

        return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
    }
}
