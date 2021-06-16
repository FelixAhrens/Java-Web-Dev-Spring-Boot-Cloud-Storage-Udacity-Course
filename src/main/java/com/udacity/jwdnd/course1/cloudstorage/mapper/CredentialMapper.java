package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
* Interface for SQL DB with MyBatis
 */

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential getCredentialByCredentialId(int credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credential> getCredentialByUserId(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, decryptedpassword, userid) VALUES(#{url}, " +
            "#{username}, #{key}, #{password}, #{decryptedPassword}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username =#{username}, key =#{key}, " +
            "password =#{password}, decryptedPassword =#{decryptedPassword} WHERE credentialid =#{credentialId}")
    boolean updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    boolean deleteCredentialByCredentialId(int credentialId);
}
