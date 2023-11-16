package com.integrador.evently.activities.controller;

import com.integrador.evently.activities.dto.ActivityDTO;
import com.integrador.evently.activities.interfaces.IActivityController;
import com.integrador.evently.activities.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController implements IActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public List<ActivityDTO> getAllActivities() {
        return activityService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById( @PathVariable Long id ) {
        ActivityDTO activityDTO = activityService.getActivityById(id);
        return (activityDTO != null)
                ? new ResponseEntity<>(activityDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<ActivityDTO> saveActivity(@RequestBody ActivityDTO activityDTO) {
        ActivityDTO savedActivity = activityService.saveActivity(activityDTO);
        return new ResponseEntity<>(savedActivity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable Long id, @RequestBody ActivityDTO activityDTO) {
        ActivityDTO updatedActivity = activityService.updateActivity(id, activityDTO);

        if (updatedActivity != null) {
            return ResponseEntity.ok(updatedActivity);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteActivity(Long id) {
        activityService.deleteActivity(id);
        //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
