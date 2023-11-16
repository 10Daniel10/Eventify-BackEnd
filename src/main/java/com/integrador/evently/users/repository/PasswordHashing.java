package com.integrador.evently.users.repository;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordHashing {

    // Generar una sal aleatoria
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    // Generar un hash de contraseña con PBKDF2
    public static byte[] hashPassword(String password, byte[] salt, int iterations, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        return secretKeyFactory.generateSecret(keySpec).getEncoded();
    }

    public static String generateHashedPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = generateSalt();
        // Generar una sal aleatoria
        int iterations = 10000;
        // Número de iteraciones (ajusta según tus necesidades)
        int keyLength = 256;
        // Longitud de la clave en bits (ajusta según tus necesidades)
        byte[] hashedPassword = hashPassword(password, salt, iterations, keyLength);

        return Base64.getEncoder().encodeToString(hashedPassword);
    }


}
