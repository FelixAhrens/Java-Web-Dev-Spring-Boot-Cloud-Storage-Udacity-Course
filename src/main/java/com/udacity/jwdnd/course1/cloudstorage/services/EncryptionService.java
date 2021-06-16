package com.udacity.jwdnd.course1.cloudstorage.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/*
* Encryption service for credentials -> password is stored encrypted in the database
 */

@Service
public class EncryptionService {
    private final Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    public String generateSecretKey() throws NoSuchAlgorithmException {
        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        return encodedKey;
    }

    public String encryptValue(String data, String key) {
        byte[] encryptedValue = null;
        byte[] decodedKey = Base64.getDecoder().decode(key);

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedValue = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IllegalBlockSizeException | BadPaddingException e) {
            logger.error(e.getMessage());
        }

        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public String decryptValue(String data, String key) {
        byte[] decryptedValue = null;
        byte[] decodedKey = Base64.getDecoder().decode(key);

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedValue = cipher.doFinal(Base64.getDecoder().decode(data));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            logger.error(e.getMessage());
        }

        return new String(decryptedValue);
    }
}
