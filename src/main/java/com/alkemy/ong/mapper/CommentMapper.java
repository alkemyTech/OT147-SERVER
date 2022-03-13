package com.alkemy.ong.mapper;


import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.entity.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper commentMapper= Mappers.getMapper(CommentMapper.class);

    CommentDto commentEntityToCommentDto(CommentEntity comment);

    CommentEntity commentDtoToCommentEntity(CommentDto dto);

}
