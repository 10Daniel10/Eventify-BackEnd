package com.integrador.evently.photo.controller;


import com.integrador.evently.photo.dto.PhotoDTO;
import com.integrador.evently.photo.interfaces.IPhotoController;
import com.integrador.evently.photo.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/photo")
public class PhotoController implements IPhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<PhotoDTO>> getPhotosByActivity(Long activityId) {
        List<PhotoDTO> photos = photoService.getPhotosByActivity(activityId);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhotoDTO> getPhotoById(@PathVariable Long id) {
        PhotoDTO photoDTO = photoService.getPhotoById(id);
        return (photoDTO != null)
                ? new ResponseEntity<>(photoDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<PhotoDTO> savePhoto(@RequestBody PhotoDTO photoDTO) {
        PhotoDTO savedPhoto = photoService.savePhoto(photoDTO);
        return new ResponseEntity<>(savedPhoto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhotoDTO> updatePhoto(@PathVariable Long id, @RequestBody PhotoDTO photoDTO) {
        PhotoDTO updatedPhoto = photoService.updatePhoto(id, photoDTO);

        if (updatedPhoto != null) {
            return ResponseEntity.ok(updatedPhoto);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable Long id) {
        photoService.deletePhoto(id);
    }
}
