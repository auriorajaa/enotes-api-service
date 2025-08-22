package com.example.enotes.service.impl;

import com.example.enotes.dto.EmailRequest;
import com.example.enotes.dto.PasswordChangeRequest;
import com.example.enotes.dto.PasswordResetRequest;
import com.example.enotes.entity.User;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.repository.UserRepository;
import com.example.enotes.service.UserService;
import com.example.enotes.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest) {
        User loggedInUser = CommonUtil.getLoggedInUser();

        if (!passwordEncoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword())) {
            throw new IllegalArgumentException("Old Password Doesn't Match");
        }

        String encodePassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
        loggedInUser.setPassword(encodePassword);
        userRepository.save(loggedInUser);
    }

    @Override
    public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception {
        User user = userRepository.findByEmail(email);

        if (ObjectUtils.isEmpty(user)) {
            throw new ResourceNotFoundException("User not found");
        }

        // Generate unique password reset token
        String passwordResetToken = UUID.randomUUID().toString();
        user.getStatus().setPasswordResetToken(passwordResetToken);
        User updateUser = userRepository.save(user);

        String url = CommonUtil.getUrl(request);
        sendEmailRequest(updateUser, url);
    }

    @Override
    public void verifyPasswordResetLink(Integer uid, String code) throws Exception {
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        verifyPasswordResetToken(user.getStatus().getPasswordResetToken(), code);
    }

    @Override
    public void resetPassword(PasswordResetRequest passwordResetRequest) throws Exception {
        User user = userRepository.findById(passwordResetRequest.getUid())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        String encodePassword = passwordEncoder.encode(passwordResetRequest.getNewPassword());

        user.setPassword(encodePassword);
        user.getStatus().setPasswordResetToken(null);
        userRepository.save(user);
    }

    private void verifyPasswordResetToken(String existToken, String reqToken) {

        if (StringUtils.hasText(reqToken)) {

            // token sudah tidak berlaku / hilang
            if (!StringUtils.hasText(existToken)) {
                throw new IllegalArgumentException("No active password reset token found. Please request a new reset link.");
            }

            // token tidak cocok
            if (!existToken.equals(reqToken)) {
                throw new IllegalArgumentException("Invalid or expired password reset token. Please request a new reset link.");
            }

        } else {
            throw new IllegalArgumentException("Password reset token is required.");
        }
    }


    private void sendEmailRequest(User user, String url) throws Exception {
        String message = ""
                + "Dear [[username]],"
                + "<br><br>We received a request to reset your password for your Enotes account."
                + "<br><br>If you made this request, please reset your password by clicking the link below:"
                + "<br><a href=\"[[url]]\">Reset your password</a>"
                + "<br><br>This link will expire after a certain period for security reasons."
                + "<br><br>If you did not request a password reset, please ignore this email or contact our support team."
                + "<br><br>Best regards,<br>The Enotes Team";

        message = message.replace("[[username]]", user.getFirstName());
        message = message.replace(
                "[[url]]",
                url + "/api/v1/home/verify-password-link?uid="
                        + user.getId()
                        + "&code="
                        + user.getStatus().getPasswordResetToken()
        );

        EmailRequest emailRequest = EmailRequest.builder()
                .to(user.getEmail())
                .title("Reset Your Enotes Password")
                .subject("Enotes Password Reset Request")
                .message(message)
                .build();

        // send password reset email
        emailService.sendEmail(emailRequest);
    }

}
