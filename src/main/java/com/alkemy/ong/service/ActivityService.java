package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.entity.ActivityEntity;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.stereotype.Service;
=======
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
>>>>>>> 0cb91f87437c1d78ab4af39dfd701204d3d61d02

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ActivityMapper activityMapper;

    @Autowired
    ActivityRepository activityRepository;

<<<<<<< HEAD
    //services to add activities
    public ActivityDto addActivity(ActivityDto activityDto){
        ActivityEntity entity = activityMapper.activityDtoToActivityEntity(activityDto);
        ActivityEntity savedEntity = activityRepository.save(entity);
        return activityMapper.activityEntityToActivityDto(savedEntity);
    }
}
=======
    public ActivityDto update(String id,ActivityDto activityDto) {
        Optional<ActivityEntity> activity = activityRepository.findById(id);
        if (activity.isPresent()) {
            ActivityEntity activityEntity = activity.get();
            activityEntity.setName(activityDto.getName());
            activityEntity.setContent(activityDto.getContent());
            activityEntity.setImage(activityDto.getImage());
            activityRepository.save(activityEntity);
            return activityMapper.activityToActivityDto(activityEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no Activity with the entered Id");
        }
    }
}
>>>>>>> 0cb91f87437c1d78ab4af39dfd701204d3d61d02
