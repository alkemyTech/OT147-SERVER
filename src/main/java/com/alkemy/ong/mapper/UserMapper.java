package com.alkemy.ong.mapper;


import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper (componentModel = "spring")
public interface UserMapper {
       UserMapper userMapper = Mappers.getMapper(UserMapper.class);
       UserDto userEntityToUserDto(UserEntity userEntity);
       UserEntity userDtoToUserEntity(UserDto userDto);
       List<UserDto> listUserEntityToListUserDto(List<UserEntity>list);
    }