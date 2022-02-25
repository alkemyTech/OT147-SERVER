package com.alkemy.ong.service;


import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUser();

    void deleteUserById(String id);

    UserEntity updateUser(UserEntity entity) ;


}
