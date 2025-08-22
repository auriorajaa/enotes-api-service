package com.example.enotes.service;

import com.example.enotes.dto.PasswordChangeRequest;
import com.example.enotes.dto.PasswordResetRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    public void changePassword(PasswordChangeRequest passwordChangeRequest);

    void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception;

    void verifyPasswordResetLink(Integer uid, String code) throws Exception;

    void resetPassword(PasswordResetRequest passwordResetRequest) throws Exception;
}
