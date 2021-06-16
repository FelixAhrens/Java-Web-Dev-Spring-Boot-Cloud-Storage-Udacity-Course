package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service for CRUD actions with note database
 */

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper){
        this.noteMapper = noteMapper;
    }

    public int addNote(String title, String description, int userId){
       return noteMapper.insertNote(new Note(null, title, description, userId));
    }

    public List<Note> getAllNotes(int userId){
        return noteMapper.getNoteByUserId(userId);
    }

    public Note getNoteByNoteId(int noteId) {return noteMapper.getNoteByNoteId(noteId);}

    public boolean deleteNote(int nodeId){
        return noteMapper.deleteNoteByNoteId(nodeId);
    }

    public boolean updateNote(int id, String title, String description, int userId){
       return noteMapper.updateNote(new Note(id, title, description, userId));
    }

}
