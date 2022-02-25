package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.dto.UserDTO;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IUserService implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public void deleteUserById(String id) {
        userRepo.deleteById(id);
    }

    @Override
    public UserEntity updateUser(UserEntity entity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(entity.getFirstName());
        userEntity.setLastName(entity.getLastName());
        userEntity.setEmail(entity.getEmail());
        userEntity.setPassword(entity.getPassword());
        userEntity.setPhoto(entity.getPhoto());
        return userRepo.save(userEntity);


    }


}
