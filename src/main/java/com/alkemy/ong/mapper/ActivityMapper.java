package com.alkemy.ong.mapper;

<<<<<<< HEAD

=======
>>>>>>> 0cb91f87437c1d78ab4af39dfd701204d3d61d02
import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.entity.ActivityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
<<<<<<< HEAD

    ActivityMapper activityMapper = Mappers.getMapper(ActivityMapper.class);

    ActivityEntity activityDtoToActivityEntity(ActivityDto activityDto);

    ActivityDto activityEntityToActivityDto(ActivityEntity activityEntity);

=======
    ActivityMapper activityMapper = Mappers.getMapper(ActivityMapper.class);

    ActivityDto activityToActivityDto(ActivityEntity activityEntity);
>>>>>>> 0cb91f87437c1d78ab4af39dfd701204d3d61d02
}
