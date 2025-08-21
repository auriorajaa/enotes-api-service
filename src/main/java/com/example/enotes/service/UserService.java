package com.example.enotes.service;

import com.example.enotes.dto.PasswordChangeRequest;

public interface UserService {

    public void changePassword(PasswordChangeRequest passwordChangeRequest);
}
