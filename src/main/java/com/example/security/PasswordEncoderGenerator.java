package com.example.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
    private static final String REGEX_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=].*[@#$%^&+=])(?=\\S+$).{8,}$"; // at least one lowercase character, at least one uppercase character, no whitespace, at least 2 special characters, at least 8 total characters
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static boolean checkPasswordValidity(String password) {
        return Pattern.matches(REGEX_PATTERN, password);
    }

    public static byte[] generateHash(String plaintextPassword, byte[] salt) {
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

    public static boolean checkPasswordMatch(String plaintextPassword, byte[] salt, byte[] expectedHash) {
        char[] password = plaintextPassword.toCharArray();
        byte[] pwdHash = generateHash(plaintextPassword, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }

    public static String generatePassword() {
        return null; // TODO: 13/01/17 (Axel): is this necessary? Consider.
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static void main(String[] args) {
        String testPassword = "abcDEF!#";
        String hashHex = "0492F50418F9D05039504B90B2F980871D5D34D340B5ABB1B0F3D5D9A12017A0";
        String saltHex = "9187DE9DADB3318455C8053DE0B62B1A";

        byte[] hashTest = hexStringToByteArray(hashHex);
        byte[] saltTest = hexStringToByteArray(saltHex);

        String testPassword2 = "@@Aa1234";
        System.out.println(checkPasswordValidity(testPassword2));
        System.out.println(checkPasswordMatch(testPassword, saltTest, hashTest));

    }
}
