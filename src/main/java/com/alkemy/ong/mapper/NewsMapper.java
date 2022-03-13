package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.entity.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsMapper newsMapper = Mappers.getMapper(NewsMapper.class);
    NewsDto newsEntityToNewsDto(NewsEntity newsEntity);
    NewsEntity newsDtoTonewsEntity(NewsDto newsDto);
    List<NewsDto> newsEntityToNewsDto(List<NewsEntity> newsEntityList);
}
