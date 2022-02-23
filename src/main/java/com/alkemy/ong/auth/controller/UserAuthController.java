package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.domain.UserDomain;
import com.alkemy.ong.auth.dto.UserDTO;
import com.alkemy.ong.auth.mapper.UserMapper;
import com.alkemy.ong.auth.service.UserDetailsCustomService;
import com.alkemy.ong.auth.dto.LoginUserDTO;
import com.alkemy.ong.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private UserDetailsCustomService userDetailsCustomService;

    @Autowired
    AuthenticationManager authenticationManager;


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


    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginUserDTO loginUserDTO) {
        UserDomain userDomain = UserMapper.LoginUserDTOToUserDomain(loginUserDTO);
        UserDTO userDTO = UserMapper.UserDomainToUserDTO(userDetailsCustomService.loginUser(userDomain));
        return ResponseEntity.ok(userDTO);
    }

}
