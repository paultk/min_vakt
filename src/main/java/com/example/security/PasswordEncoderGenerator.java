package com.example.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.regex.*;

/**
 * Created by axelkvistad on 12/01/17.
 */
public class PasswordEncoderGenerator {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public byte[] generateHash(String plaintextPassword, byte[] salt) {
        char[] password = plaintextPassword.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage());
        } finally {
            spec.clearPassword();
        }
    }

    public boolean checkPasswordMatch(String plaintextPassword, byte[] salt, byte[] expectedHash) {
        char[] password = plaintextPassword.toCharArray();
        byte[] pwdHash = generateHash(plaintextPassword, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }

    public String generatePassword() {
        return null;
    }





}
