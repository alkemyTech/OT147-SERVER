package com.alkemy.ong.auth.mapper;

import com.alkemy.ong.auth.domain.UserDomain;
import com.alkemy.ong.entity.UserEntity;

public class UserMapper {


    public static UserDomain LoginUserDTOToUserDomain(LoginUserDTO loginUserDTO) {
        UserDomain userDomain = UserDomain.builder()
                .password(loginUserDTO.getPassword())
                .email(loginUserDTO.getEmail())
                .build();
        return userDomain;
    }

    public static UserDTO UserDomainToUserDTO(UserDomain user) {
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .photo(user.getPhoto())
                .timestamps(user.getTimestamps())
                .build();
        return userDTO;
    }

    public static UserDomain userEntityToUserDomain(UserEntity userEntity) {
        UserDomain userDomain = UserDomain.builder()
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .photo(userEntity.getPhoto())
                .roleid(RoleMapper.roleEntityToRoleDomain(userEntity.getRoleId())).build();
        return userDomain;
    }
}
