package com.example.enotes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.enotes.entity.AccountStatus;
import com.example.enotes.entity.User;
import com.example.enotes.exception.ResourceNotFoundException;
import com.example.enotes.exception.SuccessException;
import com.example.enotes.repository.UserRepository;
import com.example.enotes.service.HomeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getStatus().getVerificationCode() == null) {
            throw new SuccessException("Account already verified");
        }

        if (user.getStatus().getVerificationCode().equals(verificationCode)) {
            AccountStatus status = user.getStatus();
            status.setIsActive(true);
            status.setVerificationCode(null);

            userRepo.save(user);

            return true;
        }

        return false;
    }
}
