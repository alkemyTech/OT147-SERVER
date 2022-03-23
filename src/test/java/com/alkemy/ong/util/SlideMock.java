package com.alkemy.ong.util;
import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDtoFull;
import com.alkemy.ong.entity.SlideEntity;

public class SlideMock {
    public SlideBasicDto mockSlideBasicDto(){
        SlideBasicDto dto = new SlideBasicDto();
        dto.setOrder(1);
        dto.setImageUrl("Mock image url");
        return dto;
    }
    public SlideDtoFull mockSlideFullDto(){
        SlideDtoFull fullDto = new SlideDtoFull();
        fullDto.setOrder(1);
        fullDto.setImageUrl("Mock image url");
        fullDto.setText("Mock text");
        return fullDto;
    }
    public SlideEntity mockSlideEntity(){
        SlideEntity slide = new SlideEntity();
        slide.setId("1");
        slide.setText("Mock text");
        slide.setOrder(1);
        slide.setImageUrl("Mock image url");
        slide.setSoftDelete(false);
        return slide;
    }
}
