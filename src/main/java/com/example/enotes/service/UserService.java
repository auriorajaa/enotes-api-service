package com.example.enotes.service;

import com.example.enotes.dto.LoginRequest;
import com.example.enotes.dto.LoginResponse;
import com.example.enotes.dto.UserDto;

public interface UserService {
    public Boolean register(UserDto userDto, String url) throws Exception;

    LoginResponse login(LoginRequest loginRequest);
}
