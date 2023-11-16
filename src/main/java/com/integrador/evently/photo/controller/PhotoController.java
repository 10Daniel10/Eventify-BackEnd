package com.integrador.evently.photo.controller;


import com.integrador.evently.photo.dto.PhotoDTO;
import com.integrador.evently.photo.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("/photo")
public class PhotoController {

    private PhotoService photoService;

    PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.photoService.uploadFile(file);
    }
    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.photoService.deleteFileFromS3Bucket(fileUrl);
    }
}
