package com.integrador.evently.photo.interfaces;

import com.integrador.evently.photo.dto.PhotoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPhotoController {
        ResponseEntity<List<PhotoDTO>> getPhotosByActivity(Long activityId);
        ResponseEntity<PhotoDTO> getPhotoById( Long id);
        ResponseEntity<PhotoDTO> savePhoto( PhotoDTO photoDTO);
        ResponseEntity<PhotoDTO> updatePhoto( Long id, PhotoDTO photoDTO);
        void deletePhoto( Long id);
}
