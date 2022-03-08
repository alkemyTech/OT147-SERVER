package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDtoFull;
import com.alkemy.ong.entity.SlideEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SlideMapper {
    SlideMapper slideMapper= Mappers.getMapper(SlideMapper.class);
    List<SlideBasicDto> listSlideEntityToListSlideBasicDto(List<SlideEntity>list);
    List<SlideDtoFull> listSlideEntityToListSlideDtoFull(List<SlideEntity>list);
    SlideDtoFull slideEntityToSlideDtoFull(SlideEntity slideEntity);
}
