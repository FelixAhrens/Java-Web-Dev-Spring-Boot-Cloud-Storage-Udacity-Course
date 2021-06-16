package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/*
 * Service for CRUD actions with credential database
 */

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredential(String url, String username, String password, int userId) {
       String key = "abc";
        try {
            key = encryptionService.generateSecretKey();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: auto generate key at CredentialService");
        }
        String encryptedPassword = encryptionService.encryptValue(password, key);
        //no decrypted password in db
        String decryptedPassword = null;
        return credentialMapper.insertCredential(new Credential(null, url, username, key,
                encryptedPassword, decryptedPassword, userId));
    }

    public List<Credential> getAllCredentials(int userId){
        List<Credential> credentialList = credentialMapper.getCredentialByUserId(userId);
        for (Credential credential : credentialList){
            //decrypt password for frontend
            credential.setDecryptedPassword(decryptPassword(credential.getCredentialId()));
        }
        return credentialList;
    }

    public Credential getCredentialByCredentialId(int credentialId){
        Credential credential = credentialMapper.getCredentialByCredentialId(credentialId);
        //decrypt password for frontend
        credential.setDecryptedPassword(decryptPassword(credentialId));
        return credential;
    }

    public boolean deleteCredential(int credentialId){
        return credentialMapper.deleteCredentialByCredentialId(credentialId);
    }

    public boolean updateCredential(int credentialId, String url, String username, String password, int userId){
        String key = credentialMapper.getCredentialByCredentialId(credentialId).getKey();
        String encryptedPassword = encryptionService.encryptValue(password, key);
        //no decrypted password in db
        String decryptedPassword = null;
        return credentialMapper.updateCredential(new Credential(credentialId, url, username, key,
                encryptedPassword, decryptedPassword, userId));
    }

    public String decryptPassword(int credentialId){
        String password = credentialMapper.getCredentialByCredentialId(credentialId).getPassword();
        String key = credentialMapper.getCredentialByCredentialId(credentialId).getKey();
        return encryptionService.decryptValue(password, key);
    }
}
