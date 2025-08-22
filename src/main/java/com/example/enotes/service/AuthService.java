package com.example.enotes.service;

import com.example.enotes.dto.LoginRequest;
import com.example.enotes.dto.LoginResponse;
import com.example.enotes.dto.UserRequest;

public interface AuthService {
    public Boolean register(UserRequest userRequest, String url) throws Exception;

    LoginResponse login(LoginRequest loginRequest);
}
