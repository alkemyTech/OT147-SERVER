package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.dto.AuthRequestDTO;
import com.alkemy.ong.auth.dto.AuthResponseDTO;
import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.auth.service.UserDetailsCustomService;
import com.alkemy.ong.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private UserDetailsCustomService userDetailsCustomService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtTokenUtil;

    public UserAuthController(UserDetailsCustomService userDetailsCustomService, AuthenticationManager authenticationManager, JwtUtils jwtTokenUtil) {
        this.userDetailsCustomService = userDetailsCustomService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
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

    /*Method to authenticate, the server generates a JWT with the user's data and will return it as a response.*/

    @PostMapping("/singin")
    public ResponseEntity<AuthResponseDTO> singin(@RequestBody AuthRequestDTO authRequest) throws Exception {
        Authentication auth;
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        final String jwt = jwtTokenUtil.generateToken(auth);
        return ResponseEntity.ok(new AuthResponseDTO(jwt));
    }

}
