package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.entity.ActivityEntity;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ActivityMapper activityMapper;

    @Autowired
    ActivityRepository activityRepository;

    //services to add activities
    public ActivityDto addActivity(ActivityDto activityDto){
        ActivityEntity entity = activityMapper.activityDtoToActivityEntity(activityDto);
        ActivityEntity savedEntity = activityRepository.save(entity);
        return activityMapper.activityEntityToActivityDto(savedEntity);
    }
}
