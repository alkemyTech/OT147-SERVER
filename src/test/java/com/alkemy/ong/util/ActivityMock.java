package com.alkemy.ong.util;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.entity.ActivityEntity;

public class ActivityMock {
    //ActivityEntity
    public ActivityEntity mockActivityEntity() {
        ActivityEntity entity = new ActivityEntity();
        entity.setId("10");
        entity.setName("Mock Name");
        entity.setContent("Mock content");
        entity.setImage("Mock image");
        entity.setSoftDelete(false);
        return entity;
    }
    //ActivityDto
//    public ActivityDto mockActivityDto  () {
//        ActivityDto dto = new ActivityDto();
//        dto.setName("Mock Name");
//        dto.setContent("Mock content");
//        dto.setImage("Mock image");
//        return dto;
//    }
}
