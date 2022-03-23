package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.entity.ActivityEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityMapperTest {
    private static ActivityMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper= Mappers.getMapper(ActivityMapper.class);

    }
    @Test
    void activityDtoToActivityEntity() {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setId("1");
        activityEntity.setName("New");
        activityEntity.setImage("this is an image");
        activityEntity.setContent("This is an address");
        activityEntity.setSoftDelete(false);

    ActivityDto activityDto = mapper.activityToActivityDto(activityEntity);
        assertAll(
                () -> {
                    assertEquals(activityEntity.getName(), activityDto.getName());
                    assertEquals(activityEntity.getImage(),activityDto.getImage());
                    assertEquals(activityEntity.getContent(),activityDto.getContent());
                }
        );
    }
    @Test
    public void activityDtoMapperTestSimpleNull(){
        ActivityEntity entity=mapper.activityDtoToActivityEntity(null);
        assertEquals(null,entity);
    }
    @Test
    void activityToActivityDto() {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setName("New");
        activityDto.setImage("this is an image");
        activityDto.setContent("This is an address");
        ActivityEntity activityEntity = mapper.activityDtoToActivityEntity(activityDto);
        assertAll(
                () -> {
                    assertEquals(activityEntity.getName(), activityDto.getName());
                    assertEquals(activityEntity.getImage(),activityDto.getImage());
                    assertEquals(activityEntity.getContent(),activityDto.getContent());
                }
        );
    }
    @Test
    public void activityEntityMapperTestSimpleNull(){
        ActivityDto dto=mapper.activityToActivityDto(null);
        assertEquals(null,dto);
    }
}
