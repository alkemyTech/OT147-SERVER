package com.alkemy.ong.service;

<<<<<<< HEAD
import com.alkemy.ong.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUser();

    void deleteUserById(String id);
=======
import com.alkemy.ong.auth.dto.UserDTO;
import com.alkemy.ong.entity.UserEntity;



public interface UserService {
      void deleteUserById(String id);
      UserEntity updateUser(UserEntity entity) ;
>>>>>>> 789510a8cb495fbf387eebdfd6e96bacb8b7d69d
}
