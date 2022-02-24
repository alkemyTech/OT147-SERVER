package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.domain.UserDomain;
import com.alkemy.ong.auth.dto.UserDTO;
import com.alkemy.ong.auth.mapper.UserMapper;
import com.alkemy.ong.auth.service.UserDetailsCustomService;
import com.alkemy.ong.auth.dto.LoginUserDTO;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class UserAuthController {

    private UserDetailsCustomService userDetailsCustomService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;


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

    @PatchMapping("/user/{id}")
    public ResponseEntity<UserDTO> update( @PathVariable String id) {

           Optional<UserEntity> entities = userRepository.findById(id);
            if (entities.isPresent()) {
                UserDTO dto = UserMapper.updateUserEntityToDto(entities);
                UserEntity userEntity = UserMapper.updateUserDTOToEntity(dto);
                userDetailsCustomService.update(userEntity);
                return ResponseEntity.ok(dto);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
