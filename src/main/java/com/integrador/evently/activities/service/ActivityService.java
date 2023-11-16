package com.integrador.evently.activities.service;

import com.integrador.evently.activities.dto.ActivityDTO;
import com.integrador.evently.activities.interfaces.IActivityService;
import com.integrador.evently.activities.model.Activity;
import com.integrador.evently.activities.repository.ActivityRepository;
import com.integrador.evently.users.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService implements IActivityService {

    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;

    public ActivityService(ActivityRepository activityRepository, ModelMapper modelMapper) {
        this.activityRepository = activityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ActivityDTO> getAll() {
        List<Activity> activities = activityRepository.findAll();
        return activities.stream()
                .map(activity -> modelMapper.map(activity, ActivityDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ActivityDTO getActivityById(Long id) {
        Activity activity = activityRepository.findById(id).orElse(null);
        return (activity != null) ? modelMapper.map(activity, ActivityDTO.class) : null;
    }

    @Override
    public ActivityDTO saveActivity(ActivityDTO activityDTO) {
        Activity activity = modelMapper.map(activityDTO, Activity.class);
        activity = activityRepository.save(activity);
        return modelMapper.map(activity, ActivityDTO.class);
    }

    @Override
    public ActivityDTO updateActivity(Long id, ActivityDTO activityDTO) {
        Activity activity = activityRepository.findById(id).orElse(null);

        if (activity != null) {
            setActivity(activity, activityDTO);
            Activity updatedActivity = activityRepository.save(activity);
            return modelMapper.map(updatedActivity, ActivityDTO.class);
        }

        return null;
    }

    @Override
    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }

    private void setActivity(Activity activity, ActivityDTO activityDTO) {
        activity.setName(activityDTO.getName());
        activity.setInformation(activityDTO.getInformation());
        activity.setCreationDate(activityDTO.getCreationDate());
        activity.setUpdateDate(activityDTO.getUpdateDate());
        activity.setAddress(activityDTO.getAddress());
        activity.setPrice(activityDTO.getPrice());
    }
}
