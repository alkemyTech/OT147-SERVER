package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.RoleEntity;
import com.alkemy.ong.entity.UserEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private static UserMapper mapper;

    @BeforeAll
    public static void setUp() {

        mapper = Mappers.getMapper(UserMapper.class);

    }

    @Test
    void userEntityToUserDto() {
        RoleEntity roleEntity = new RoleEntity();
        UserEntity userEntity = new UserEntity();
        userEntity.setId("125478");
        userEntity.setFirstName("Dante");
        userEntity.setLastName("Iturri");
        userEntity.setEmail("dante@gmail.com");
        userEntity.setPassword("123456");
        userEntity.setPhoto("default.jpg");
        userEntity.setRoleId(roleEntity);
        userEntity.setTimestamps(LocalDateTime.now());


        UserDto userDto = mapper.userEntityToUserDto(userEntity);
        assertAll(
                () -> {
                    assertEquals(userEntity.getId(),userDto.getId());
                    assertEquals(userEntity.getFirstName(),userDto.getFirstName());
                    assertEquals(userEntity.getLastName(),userDto.getLastName());
                    assertEquals(userEntity.getEmail(),userDto.getEmail());
                    assertEquals(userEntity.getPassword(),userDto.getPassword());
                    assertEquals(userEntity.getPhoto(),userDto.getPhoto());
                    assertEquals(userEntity.getRoleId(),userDto.getRoleId());
                    assertEquals(userEntity.getTimestamps(),userDto.getTimestamps());
                }
        );
    }
    @Test
    void userEntityToUserDtoNull() {
        UserEntity userEntity = null;
        UserDto userDto = mapper.userEntityToUserDto(userEntity);
        assertEquals(null,userDto);
    }


    @Test
    void userDtoToUserEntity() {
        RoleEntity roleEntity = new RoleEntity();
        UserDto userDto = new UserDto();
        userDto.setId("125478");
        userDto.setFirstName("Dante");
        userDto.setLastName("Iturri");
        userDto.setEmail("dante@gmail.com");
        userDto.setPassword("123456");
        userDto.setPhoto("default.jpg");
        userDto.setRoleId(roleEntity);
        userDto.setTimestamps(LocalDateTime.now());

        UserEntity userEntity = mapper.userDtoToUserEntity(userDto);
        assertAll(
                () -> {
                    assertEquals(userDto.getId(),userEntity.getId());
                    assertEquals(userDto.getFirstName(),userEntity.getFirstName());
                    assertEquals(userDto.getLastName(),userEntity.getLastName());
                    assertEquals(userDto.getEmail(),userEntity.getEmail());
                    assertEquals(userDto.getPassword(),userEntity.getPassword());
                    assertEquals(userDto.getPhoto(),userEntity.getPhoto());
                    assertEquals(userDto.getRoleId(),userEntity.getRoleId());
                    assertEquals(userDto.getTimestamps(),userEntity.getTimestamps());
                }
        );
    }
    @Test
    void userDtoToUserEntityNull() {
        UserDto userDto = null;
        UserEntity userEntity = mapper.userDtoToUserEntity(userDto);
        assertNull(userEntity);
    }
    @Test
    void listUserEntityToListUserDto() {
        RoleEntity roleEntity = new RoleEntity();
        UserEntity userList = new UserEntity();
        userList.setId("125478");
        userList.setFirstName("Dante");
        userList.setLastName("Iturri");
        userList.setEmail("dante@gmail.com");
        userList.setPassword("123456");
        userList.setPhoto("default.jpg");
        userList.setRoleId(roleEntity);
        userList.setTimestamps(LocalDateTime.now());
        List<UserEntity> userEntityList = new ArrayList<>(List.of(userList));
        List<UserDto> userDtoList = mapper.listUserEntityToListUserDto(userEntityList);
        assertAll(
                ()-> {
                    assertEquals(userList.getId(),userDtoList.get(0).getId());
                    assertEquals(userList.getFirstName(),userDtoList.get(0).getFirstName());
                    assertEquals(userList.getLastName(),userDtoList.get(0).getLastName());
                    assertEquals(userList.getEmail(),userDtoList.get(0).getEmail());
                    assertEquals(userList.getPassword(),userDtoList.get(0).getPassword());
                    assertEquals(userList.getPhoto(),userDtoList.get(0).getPhoto());
                    assertEquals(userList.getRoleId(),userDtoList.get(0).getRoleId());
                    assertEquals(userList.getTimestamps(),userDtoList.get(0).getTimestamps());
                });

    }
    @Test
    void listUserEntityToListUserDtoNull() {
        List<UserEntity> userEntityList = null;
        List<UserDto> userDtoList = mapper.listUserEntityToListUserDto(userEntityList);
        assertNull(userDtoList);
    }
}