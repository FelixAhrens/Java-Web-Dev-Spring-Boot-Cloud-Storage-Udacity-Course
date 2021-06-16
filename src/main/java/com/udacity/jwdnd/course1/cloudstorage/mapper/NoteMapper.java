package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
 * Interface for SQL DB with MyBatis
 */

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNoteByNoteId(int noteId);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getNoteByUserId(int userId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription =#{noteDescription} WHERE noteid =#{noteId}")
    boolean updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    boolean deleteNoteByNoteId(int noteId);
}
