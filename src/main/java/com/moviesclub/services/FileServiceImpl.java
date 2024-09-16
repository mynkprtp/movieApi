package com.moviesclub.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // get name of File
        String fileName = file.getOriginalFilename();

        // to get the file path where we want to upload
        String filePath = path + File.separator + fileName;
        // File.separator tells that suffix needs to be appended in the prefix as file path

        // create file object
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        // copy the file or upload the file
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        return new FileInputStream(filePath);
    }
}
