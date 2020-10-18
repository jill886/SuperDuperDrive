package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> checkForDuplicates(Integer userId, String fileName){
        return fileMapper.selectByNameAndUserId(userId, fileName);
    }

    public int uploadFile(File file){
        return fileMapper.insertFile(file);
    }

    public File downloadFile(Integer fileId){
        return fileMapper.selectById(fileId);
    }

    public int deleteFile(Integer fileId){
        return fileMapper.deleteFile(fileId);
    }

    public List<File> getAllFilesForUser(Integer userId){
        return fileMapper.selectAllFilesByUID(userId);
    }

//    public List<File> getAllFiles(){ return fileMapper.selectAllFiles();}

}
