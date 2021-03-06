package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
 * Interface for SQL DB with MyBatis
 */

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFileByFileId(int fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFileByUserId(int userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    boolean deleteFileByFileId(int fileId);
}
