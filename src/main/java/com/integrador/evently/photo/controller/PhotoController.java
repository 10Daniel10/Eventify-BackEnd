package com.integrador.evently.photo.controller;

import com.integrador.evently.photo.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/photos")
@CrossOrigin
public class PhotoController {

    private PhotoService photoService;

    PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/users/{userId}/upload")
    public String uploadUserFile(@RequestPart(value = "file") MultipartFile file,
                             @PathVariable Long userId) {
        return this.photoService.uploadUserFile(file, userId);
    }

    @PostMapping("/products/{productId}/upload")
    public String uploadProductFile(@RequestPart(value = "file") MultipartFile file,
                                 @PathVariable Long productId) {
        return this.photoService.uploadProductFile(file, productId);
    }

    @PostMapping("/provider/{providerId}/upload")
    public String uploadProviderFile(@RequestPart(value = "file") MultipartFile file,
                                    @PathVariable Long providerId) {
        return this.photoService.uploadProviderFile(file, providerId);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.photoService.deleteFileFromS3Bucket(fileUrl);
    }
}
