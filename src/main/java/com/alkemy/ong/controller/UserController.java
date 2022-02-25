package com.alkemy.ong.controller;

<<<<<<< HEAD
import com.alkemy.ong.dto.UserDTO;
=======
import com.alkemy.ong.auth.dto.UserDTO;
import com.alkemy.ong.auth.mapper.UserMapper;
>>>>>>> 789510a8cb495fbf387eebdfd6e96bacb8b7d69d
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> 789510a8cb495fbf387eebdfd6e96bacb8b7d69d

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    //method to get a list of users
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> UserList() {
        List<UserDTO> users = userService.getAllUser();
        return ResponseEntity.ok().body(users);
    }

    @Autowired
    private UserRepository userRepository;



    //Method for hard delete
    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> delete(@PathVariable String id) {
        this.userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    /*Method for actualization for id */
    @PatchMapping("/user/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable String id) {
        try{

            UserEntity entity = userRepository.findById(id).get();

            return new ResponseEntity<UserDTO>(UserMapper
                    .updateUserEntityToDTO(userService
                            .updateUser(entity)),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }
}
