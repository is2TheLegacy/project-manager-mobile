/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.fpuna.is2.proyectos.alpha.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rafae
 */
public final class SecurityUtil {

    private SecurityUtil() {
    }

    private static String toHex(byte[] byteStream) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteStream.length; i++) {
            String hex = Integer.toHexString(0xff & byteStream[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static String generateRandomSalt() {
        byte[] array = new byte[16];
        new Random().nextBytes(array);
        return toHex(array);
    }

    public static String digestText(String text) throws SecurityException {
        String digestResult = null;
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            digestResult = toHex(encodedhash);
        } catch (NoSuchAlgorithmException ex) {
            throw new SecurityException("No se pudo aplicar el digest.", ex);
        }
        return digestResult;
    }
    
    public static boolean isStrongPassword(String password) {
        return password != null && password.length() >= 8;
    }
}
