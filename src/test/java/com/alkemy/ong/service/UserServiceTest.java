package com.alkemy.ong.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.alkemy.ong.auth.dto.UserDTO;
import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.RoleEntity;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.repository.UserRepository;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    private UserEntity userEntity;
    private UserEntity userEntity1;



    @Test
    void testDeleteUserById() {
        doNothing().when(this.userRepository).deleteById((String) any());
        Mockito.when(this.userRepository.existsById((String) any())).thenReturn(true);
        this.userService.deleteUserById("42");
        verify(this.userRepository).existsById((String) any());
        verify(this.userRepository).deleteById((String) any());
        assertTrue(this.userService.getAllUser().isEmpty());
    }

    @Test
    void testDeleteUserById_ParamNotFound() {
        doThrow(new ParamNotFound("An error occurred")).when(this.userRepository).deleteById((String) any());
        Mockito.when(this.userRepository.existsById((String) any())).thenReturn(true);
        assertThrows(ParamNotFound.class, () -> this.userService.deleteUserById("42"));
        verify(this.userRepository).existsById((String) any());
        verify(this.userRepository).deleteById((String) any());
    }

    @Test
    void testDeleteUserById_ExistId() {
        doNothing().when(this.userRepository).deleteById((String) any());
        Mockito.when(this.userRepository.existsById((String) any())).thenReturn(false);
        assertThrows(ParamNotFound.class, () -> this.userService.deleteUserById("42"));
        verify(this.userRepository).existsById((String) any());
    }

    @Test
    void testGetAllUser_ListIsEmpty() {
        Mockito.when(this.userRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.userService.getAllUser().isEmpty());
        verify(this.userRepository).findAll();
    }

    @Test
    void testGetAllUser_ParamNotFound() throws ParamNotFound{
        Mockito.when(this.userRepository.findAll()).thenThrow(new ParamNotFound("An error occurred"));
        assertThrows(ParamNotFound.class, () -> this.userService.getAllUser());
        verify(this.userRepository).findAll();
    }

    @Test
    void testGetAllUser_Null() {
        List<UserEntity> listUser = null;
        Mockito.when(this.userRepository.findAll()).thenReturn(null);
        assertThrows(ParamNotFound.class, () -> this.userService.getAllUser());
        verify(this.userRepository).findAll();

    }

    @Test
    void testUpdateUser() {
        RoleEntity roleEntity = new RoleEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFirstName("Jane");
        userEntity.setId("42");
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setPhoto("alice.liddell@example.org");
        userEntity.setRoleId(roleEntity);
        userEntity.setSoftDelete(true);
        userEntity.setTimestamps(LocalDateTime.of(1, 1, 1, 1, 1));
        Optional<UserEntity> ofResult = Optional.of(userEntity);

        RoleEntity roleEntity1 = new RoleEntity();
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFirstName("Jane");
        userEntity1.setId("42");
        userEntity1.setLastName("Doe");
        userEntity1.setPassword("iloveyou");
        userEntity1.setPhoto("alice.liddell@example.org");
        userEntity1.setRoleId(roleEntity1);
        userEntity1.setSoftDelete(true);
        userEntity1.setTimestamps(LocalDateTime.of(1, 1, 1, 1, 1));

        Mockito.when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity1);
        Mockito.when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        UserDto userDto = mock(UserDto.class);
        Mockito.when(userDto.getEmail()).thenReturn("jane.doe@example.org");
        Mockito.when(userDto.getFirstName()).thenReturn("Jane");
        Mockito.when(userDto.getLastName()).thenReturn("Doe");
        Mockito.when(userDto.getPhoto()).thenReturn("alice.liddell@example.org");
        UserDto actualUpdateUserResult = this.userService.updateUser("42", userDto);
        assertEquals("jane.doe@example.org", actualUpdateUserResult.getEmail());
        assertSame(roleEntity, actualUpdateUserResult.getRoleId());
        assertEquals("01:01", actualUpdateUserResult.getTimestamps().toLocalTime().toString());
        assertEquals("Jane", actualUpdateUserResult.getFirstName());
        assertEquals("Doe", actualUpdateUserResult.getLastName());
        assertEquals("iloveyou", actualUpdateUserResult.getPassword());
        assertEquals("42", actualUpdateUserResult.getId());
        assertEquals("alice.liddell@example.org", actualUpdateUserResult.getPhoto());
        verify(this.userRepository).save((UserEntity) any());
        verify(this.userRepository, atLeast(1)).findById((String) any());
        verify(userDto).getEmail();
        verify(userDto).getFirstName();
        verify(userDto).getLastName();
        verify(userDto).getPhoto();
    }

    @Test
    void testUpdateUserVerifiedParams() {
        RoleEntity roleEntity = new RoleEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFirstName("Jane");
        userEntity.setId("42");
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setPhoto("alice.liddell@example.org");
        userEntity.setRoleId(roleEntity);
        userEntity.setSoftDelete(true);
        userEntity.setTimestamps(LocalDateTime.of(1, 1, 1, 1, 1));
        Optional<UserEntity> ofResult = Optional.of(userEntity);

        RoleEntity roleEntity1 = new RoleEntity();
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFirstName("Jane");
        userEntity1.setId("42");
        userEntity1.setLastName("Doe");
        userEntity1.setPassword("iloveyou");
        userEntity1.setPhoto("alice.liddell@example.org");
        userEntity1.setRoleId(roleEntity1);
        userEntity1.setSoftDelete(true);
        userEntity1.setTimestamps(LocalDateTime.of(1, 1, 1, 1, 1));
        Mockito.when(this.userRepository.save((UserEntity) any())).thenReturn(userEntity1);
        Mockito.when(this.userRepository.findById((String) any())).thenReturn(ofResult);
        UserDto userDto= mock(UserDto.class);
        Mockito.when(userDto.getEmail()).thenThrow(new ParamNotFound("An error occurred"));
        Mockito.when(userDto.getFirstName()).thenThrow(new ParamNotFound("An error occurred"));
        Mockito.when(userDto.getLastName()).thenThrow(new ParamNotFound("An error occurred"));
        Mockito.when(userDto.getPhoto()).thenThrow(new ParamNotFound("An error occurred"));
        assertThrows(ParamNotFound.class, () -> this.userService.updateUser("42", userDto));
        verify(this.userRepository, atLeast(1)).findById((String) any());
        verify(userDto).getFirstName();
    }
    @Test

    void testUpdateUser_ParamNotFoundValidate(){
        Mockito.when(this.userRepository.existsById((String) any())).thenReturn(false);
        assertThrows(ParamNotFound.class, () -> this.userService.updateUser("42",(UserDto) any()));
        verify(this.userRepository).findById((String) any());
    }

    @Test
    void testUserMe() {
        Mockito.when(this.jwtUtils.extractUsername((String) any())).thenReturn("janedoe");
        this.userService.userMe("jwtUser");
    }

    @Test
    void testUserMe_Details() {
        RoleEntity roleEntity = new RoleEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("jane.doe@example.org");
        userEntity.setFirstName("Jane");
        userEntity.setId("42");
        userEntity.setLastName("Doe");
        userEntity.setPassword("iloveyou");
        userEntity.setPhoto("alice.liddell@example.org");
        userEntity.setRoleId(roleEntity);
        userEntity.setSoftDelete(true);
        userEntity.setTimestamps(LocalDateTime.of(1, 1, 1, 1, 1));
        Optional<UserEntity> ofResult = Optional.of(userEntity);

        RoleEntity roleEntity1 = new RoleEntity();
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setEmail("jane.doe@example.org");
        userEntity1.setFirstName("Jane");
        userEntity1.setId("42");
        userEntity1.setLastName("Doe");
        userEntity1.setPassword("iloveyou");
        userEntity1.setPhoto("alice.liddell@example.org");
        userEntity1.setRoleId(roleEntity1);
        userEntity1.setSoftDelete(true);
        userEntity1.setTimestamps(LocalDateTime.of(1, 1, 1, 1, 1));
        Mockito.when(this.userRepository.findByEmail((String) any())).thenReturn(userEntity);
        Mockito.when(this.jwtUtils.extractUsername((String) any())).thenReturn("janedoe");
        UserDto actualUserMeResult = this.userService.userMe("jwtUser");
        assertEquals("jane.doe@example.org", actualUserMeResult.getEmail());
        assertSame(roleEntity, actualUserMeResult.getRoleId());
        assertEquals("01:01", actualUserMeResult.getTimestamps().toLocalTime().toString());
        assertEquals("Jane", actualUserMeResult.getFirstName());
        assertEquals("Doe", actualUserMeResult.getLastName());
        assertEquals("iloveyou", actualUserMeResult.getPassword());
        assertEquals("42", actualUserMeResult.getId());
        assertEquals("alice.liddell@example.org", actualUserMeResult.getPhoto());
        verify(this.userRepository).findByEmail((String) any());
        verify(this.jwtUtils).extractUsername((String) any());
    }


}

