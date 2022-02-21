package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.service.UserDetailsCustomService;
import com.alkemy.ong.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private UserDetailsCustomService userDetailsCustomService;


    public UserAuthController(UserDetailsCustomService userDetailsCustomService) {
        this.userDetailsCustomService = userDetailsCustomService;
    }

    /*
    //SIGNUP
    I check that a user can be registered. Inclusion of @Valid in UserEntity to
    validate firstname, lastname, email and password.
     */
    @PostMapping("/register")
    public ResponseEntity<UserEntity> signup(@RequestBody @Valid UserEntity user) {
        userDetailsCustomService.save(user);
        return new ResponseEntity<UserEntity>(user, HttpStatus.CREATED);
    }
}
