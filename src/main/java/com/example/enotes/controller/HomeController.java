package com.example.enotes.controller;

import com.example.enotes.dto.PasswordResetRequest;
import com.example.enotes.endpoint.HomeEndpoint;
import com.example.enotes.service.HomeService;
import com.example.enotes.service.UserService;
import com.example.enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class HomeController implements HomeEndpoint {

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid, @RequestParam String code) throws Exception {
        Boolean verifyAccount = homeService.verifyAccount(uid, code);

        if (verifyAccount) return CommonUtil.createBuildResponseMessage("Account verified successfully", HttpStatus.OK);

        return  CommonUtil.createBuildResponseMessage("Account verification failed", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception {
        userService.sendEmailPasswordReset(email, request);

        return CommonUtil.createBuildResponseMessage("Email sent successfully. Please check your email for further information", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception {
        userService.verifyPasswordResetLink(uid, code);

        return CommonUtil.createBuildResponseMessage("Link verified successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception {
        userService.resetPassword(passwordResetRequest);

        return CommonUtil.createBuildResponseMessage("Password reset successfully", HttpStatus.OK);
    }

}
