package ru.itis.service;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptService {

    private static final int LOG_ROUNDS = 10;

    public static String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}
