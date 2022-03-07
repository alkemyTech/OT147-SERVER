package com.alkemy.ong.service;



import com.alkemy.ong.auth.dto.AuthResponseDTO;
import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.alkemy.ong.mapper.UserMapper.userMapper;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

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
            return userMapper.userEntityToUserDto(userEntity);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no User with the entered Id");
        }
    }

//Method that searches the user data by token
    public UserDto userMe(String jwt) {
        final String token = jwt.substring(7);
        String username = jwtUtils.extractUsername(token);
        UserEntity userEntity = userRepository.findByEmail(username);
        UserDto dto = userMapper.userEntityToUserDto(userEntity);

        return dto;
    }
}
