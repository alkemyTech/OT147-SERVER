package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.NewsDTO;
import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.entity.NewsEntity;
import com.alkemy.ong.entity.OrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsMapper {
    NewsMapper newsMapper = Mappers.getMapper(NewsMapper.class);
    NewsDTO newsEntityToNewsDTO(NewsEntity newsEntity);
    NewsEntity newsDTOTonewsEntity(NewsDTO newsDTO);
}
