package com.project.crowdfunding.Services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public String uploadImage(MultipartFile file, String fileType);

    public void deleteImage(String path);

}
