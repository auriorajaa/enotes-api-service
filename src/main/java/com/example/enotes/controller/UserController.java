package com.example.enotes.controller;

import com.example.enotes.dto.PasswordChangeRequest;
import com.example.enotes.dto.UserResponse;
import com.example.enotes.endpoint.UserEndpoint;
import com.example.enotes.entity.User;
import com.example.enotes.service.UserService;
import com.example.enotes.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController implements UserEndpoint {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> getProfile() {
        User loggedInUser = CommonUtil.getLoggedInUser();
        UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);

        return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        userService.changePassword(passwordChangeRequest);

        return CommonUtil.createBuildResponseMessage("Password Changed Successfully", HttpStatus.OK);
    }
}
