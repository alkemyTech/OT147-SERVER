package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.dto.AuthResponseDTO;
import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    @Transactional
    public List<UserDTO> getAllUser() {
        List<UserEntity> entities = userRepository.findAll();
        List<UserDTO> result = userMapper.userEntityListToDTOList(entities);
        return result;
    }

    @Override
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity updateUser(UserEntity entity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(entity.getFirstName());
        userEntity.setLastName(entity.getLastName());
        userEntity.setEmail(entity.getEmail());
        userEntity.setPassword(entity.getPassword());
        userEntity.setPhoto(entity.getPhoto());
        return userRepository.save(userEntity);

    }

    @Override
    public UserDTO userMe(AuthResponseDTO jwt) {

        String username = jwtUtils.extractUsername(jwt.toString());
        UserEntity userEntity = userRepository.findByEmail(username);
        UserDTO dto = userMapper.userEntityToUserDTO(userEntity);


        return dto;
    }
}
