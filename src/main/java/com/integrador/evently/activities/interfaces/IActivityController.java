package com.integrador.evently.activities.interfaces;

import com.integrador.evently.activities.dto.ActivityDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IActivityController {

    List<ActivityDTO> getAllActivities();
    ResponseEntity<ActivityDTO> getActivityById(Long id );
    ResponseEntity<ActivityDTO> saveActivity( ActivityDTO activityDTO );
    ResponseEntity<ActivityDTO> updateActivity( Long id, ActivityDTO activityDTO );
    void deleteActivity( Long id);
}
