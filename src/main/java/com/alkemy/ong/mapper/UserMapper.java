package com.alkemy.ong.mapper;

import com.alkemy.ong.auth.mapper.RoleMapper;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserEntity userDTOToUserEntity(UserDTO dto) {
        UserEntity userEntity = UserEntity.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .photo(dto.getPhoto())
                .timestamps(dto.getTimestamps())
                .roleId(dto.getRoleId())
                .build();
        return userEntity;
    }

    public UserDTO userEntityToUserDTO(UserEntity userEntity) {
        UserDTO userDTO = UserDTO.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .photo(userEntity.getPhoto())
                .timestamps(userEntity.getTimestamps())
                .roleId(userEntity.getRoleId())
                .build();
        return userDTO;
    }

    public List<UserDTO> userEntityListToDTOList(List<UserEntity> entities) {
        List<UserDTO> usersDto = new ArrayList<>();
        for (UserEntity entity : entities) {
            usersDto.add(userEntityToUserDTO(entity));
        }
        return usersDto;
    }
}
