package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

/*
 * Interface for SQL DB with MyBatis
 */

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) " +
            "VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    @Update("UPDATE USERS SET username=#{username}, password =#{password}, firstName =#{firstName}, " +
            "lastName =#{lastName} WHERE userid =#{userId}")
    boolean updateUser(User user);

    @Delete("DELETE FROM USERS WHERE userid = #{userId}")
    boolean deleteUserById(int userId);
}
