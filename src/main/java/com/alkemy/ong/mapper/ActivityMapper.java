package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.entity.ActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    ActivityMapper activityMapper = Mappers.getMapper(ActivityMapper.class);
    ActivityEntity activityDtoToActivityEntity(ActivityDto activityDto);
    ActivityDto activityToActivityDto(ActivityEntity activityEntity);
}
