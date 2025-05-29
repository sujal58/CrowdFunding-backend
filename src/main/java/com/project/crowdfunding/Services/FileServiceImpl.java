package com.project.crowdfunding.Services;

import com.project.crowdfunding.Exception.ApiException;
import com.project.crowdfunding.utils.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{

    @Value("${spring.project.file}")
    private String imageDirectory;

    @Autowired
    private AuthHelper authHelper;


    @Override
    public String uploadImage (MultipartFile file, String fileType) {
        String username = authHelper.getAuthenticatedUsername();
        if(!Set.of("docFront", "docBack", "image", "campaign").contains(fileType)){
            throw new IllegalArgumentException("Incorrect file type: "+ fileType);
        }

        try{
            String originalFileName = Paths.get(file.getOriginalFilename()).getFileName().toString();

            String fileName = fileType+"_"+ UUID.randomUUID()+"_"+ originalFileName;

            Path userDirectory;
            if(fileType.equals("campaign")){
                userDirectory = Paths.get(imageDirectory, username, fileType);
            }else{
                userDirectory = Paths.get(imageDirectory, username);
            }

            Path filePath = userDirectory.resolve(fileName);

            Files.createDirectories(userDirectory);

            file.transferTo(filePath);

            return filePath.toString();
        }catch (Exception e){
            throw new ApiException("Error while uploading file: "+ e);
        }
    }

    @Override
    public void deleteImage(String path){
        if(path == null || path.trim().isEmpty()){
            return;
        }
        File file = new File(path);

        if(file.exists() && file.isFile()){
            if(!file.delete()){
                throw new ApiException("Error while deleting notice image!");
            }
        }

    }
}
