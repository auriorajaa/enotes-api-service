package com.example.enotes.util;

public class Constants {
    static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    static final String MOB_NO_REGEX = "^\\+?[1-9]\\d{1,14}$";

    // Minimum 8 characters, at least one uppercase letter, one lowercase letter, one digit, and one special character
    static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";

    public static final String ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
    public static final String ROLE_USER = "hasRole('ROLE_USER')";
    public static final String ROLE_ANY = "hasAnyRole('USER', 'ADMIN')";

    public static final String DEFAULT_PAGE_NO = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_KEY_VALUE = "";
}
