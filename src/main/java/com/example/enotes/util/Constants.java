package com.example.enotes.util;

public class Constants {
    static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    static final String MOB_NO_REGEX = "^\\+?[1-9]\\d{1,14}$";

    // Minimum 8 characters, at least one uppercase letter, one lowercase letter, one digit, and one special character
    static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
}
