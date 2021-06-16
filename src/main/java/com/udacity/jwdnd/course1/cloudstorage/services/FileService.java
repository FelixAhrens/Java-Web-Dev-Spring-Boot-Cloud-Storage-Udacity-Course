package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service for CRUD actions with file database
 */

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int addFile(String filename, String contentType, String fileSize, int userId, byte[] fileData) {
        return fileMapper.insertFile(new File(null, filename, contentType, fileSize, userId, fileData));
    }

    public List<File> getAllFiles(int userId) {
        return fileMapper.getFileByUserId(userId);
    }

    public File getFileByFileId(int fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    public boolean deleteFile(int fileId) {
        return fileMapper.deleteFileByFileId(fileId);
    }
}
