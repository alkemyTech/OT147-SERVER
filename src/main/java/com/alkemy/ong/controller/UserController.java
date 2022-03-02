package com.alkemy.ong.controller;

import com.alkemy.ong.auth.dto.AuthResponseDTO;
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    //method to get a list of users
    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> UserList() {
        List<UserDTO> users = userService.getAllUser();
        return ResponseEntity.ok().body(users);
    }
    @GetMapping("auth/me")
    public UserDTO userMe(AuthResponseDTO jwt){
        return userService.userMe(jwt);
    }

    // Soft deletion method
    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> delete(@PathVariable String id) {
        try {
            this.userService.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /*Method for actualization for id */
    @PatchMapping("/user/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable String id) {
        try {

            UserEntity entity = userRepository.findById(id).get();
            return new ResponseEntity<UserDTO>(UserMapper
                    .updateUserEntityToDTO(userService.updateUser(entity)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
