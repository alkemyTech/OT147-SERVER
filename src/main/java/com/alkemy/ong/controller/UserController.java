package com.alkemy.ong.controller;

import com.alkemy.ong.auth.dto.UserDTO;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "List of users, displays their data by token, updates their data and deletes users")
public class UserController {

    @Autowired
    UserService userService;


    //Method to get a list of users
    @Tag(name = "Users")
    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> UserList() {
        List<UserDto> users = userService.getAllUser();
        return ResponseEntity.ok().body(users);
    }

    //Method to fetch the user data with the token
    @Tag(name = "Users")
    @GetMapping("/auth/me")
    public UserDto userMe(@RequestHeader("Authorization") String jwt){
        UserDto dto = userService.userMe(jwt);
        return dto;
    }

    //Method for hard
    @Tag(name = "Users")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> delete(@PathVariable String id) {
        try {
        this.userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*Method for actualization for id */
    @Tag(name = "Users")
    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable String id,@RequestBody UserDTO dto ){
        try {
            return ResponseEntity.ok(userService.updateUser(id,dto));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
