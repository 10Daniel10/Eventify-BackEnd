package com.integrador.evently.photo.interfaces;

import com.integrador.evently.photo.dto.PhotoDTO;

import java.util.List;

public interface IPhotoService {
    List<PhotoDTO> getPhotosByActivity(Long activityId);
    PhotoDTO getPhotoById(Long id);
    PhotoDTO savePhoto(PhotoDTO photoDTO);
    PhotoDTO updatePhoto(Long id, PhotoDTO photoDTO);
    void deletePhoto(Long id);
}
