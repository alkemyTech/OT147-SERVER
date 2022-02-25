package com.alkemy.ong.service;

import com.alkemy.ong.auth.dto.UserDTO;
import com.alkemy.ong.entity.UserEntity;



public interface UserService {
      void deleteUserById(String id);
      UserEntity updateUser(UserEntity entity) ;
}
