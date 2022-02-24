package com.alkemy.ong.controller;

import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //Method for hard delete
    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> delete(@PathVariable String id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping("/user/{id}")
    public ResponseEntity<UserDTO> update( @PathVariable String id) {
        Optional<UserEntity> entities = userRepository.findById(id);
        if (entities.isPresent()) {
            UserDTO dto = UserMapper.updateUserEntityToDto(entities);
            UserEntity userEntity = UserMapper.updateUserDTOToEntity(dto);
            userService.update(userEntity);
            return ResponseEntity.ok(dto);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
