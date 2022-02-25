package com.alkemy.ong.controller;

<<<<<<< HEAD
import com.alkemy.ong.dto.UserDTO;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
=======
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
>>>>>>> 14cd07718014ec2e11e53a07318bcd3f83810ac4

@RestController
@RequestMapping("/users")
public class UserController {
<<<<<<< HEAD

    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> UserList(){
        List<UserDTO> users = userService.getAllUser();
        return ResponseEntity.ok().body(users);
=======
    @Autowired
    private UserService userService;

    //Method for hard delete
    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> delete(@PathVariable String id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
>>>>>>> 14cd07718014ec2e11e53a07318bcd3f83810ac4
    }
}
