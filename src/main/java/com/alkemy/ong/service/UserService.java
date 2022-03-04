package com.alkemy.ong.service;


import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }
    //Soft deletion method for the User

    public void deleteUserById(String id) throws Exception {
        userRepository.deleteById(id);
    }
    //Get all Users from Database.
    public List<UserDto> getAllUser() {
        List<UserEntity> List = userRepository.findAll();
        return UserMapper.userMapper.listUserEntityToListUserDto(List);
    }
    //Update Category
    public UserDto updateUser(String id) {
        if (userRepository.findById(id).isPresent()) {
            UserEntity userEntity = userRepository.findById(id).get();
            userEntity.setFirstName(userEntity.getFirstName());
            userEntity.setLastName(userEntity.getLastName());
            userEntity.setEmail(userEntity.getEmail());
            userEntity.setPassword(userEntity.getPassword());
            userEntity.setPhoto(userEntity.getPhoto());
            userEntity.setRoleId(userEntity.getRoleId());
            userEntity.setSoftDelete(userEntity.getSoftDelete());
                        userRepository.save(userEntity);
            return UserMapper.userMapper.userEntityToUserDto(userEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no User with the entered Id");
        }
    }




//    @Override
//    public UserDTO userMe(AuthResponseDTO jwt) {
//
//        String username = jwtUtils.extractUsername(jwt.toString());
//        UserEntity userEntity = userRepository.findByEmail(username);
//        UserDTO dto = userMapper.userEntityToUserDTO(userEntity);
//
//
//        return dto;
//    }
}
