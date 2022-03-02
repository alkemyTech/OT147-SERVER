package com.alkemy.ong.service;


import com.alkemy.ong.auth.dto.AuthResponseDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUser();

    void deleteUserById(String id);

    UserEntity updateUser(UserEntity entity) ;

    UserDTO userMe(AuthResponseDTO jwt);

}
