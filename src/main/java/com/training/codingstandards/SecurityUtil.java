package com.training.codingstandards;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurityUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    private SecurityUtil() {
    }

    public static String hashIdentifier(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte item : digest) {
                sb.append(String.format("%02x", item));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    public static String sessionToken() {
        byte[] token = new byte[32];
        RANDOM.nextBytes(token);
        StringBuilder result = new StringBuilder(token.length * 2);
        for (byte item : token) {
            result.append(String.format("%02x", item));
        }
        return result.toString();
    }
}
