package com.alkemy.ong.controller;


import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.RoleEntity;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.mapper.UserMapperImpl;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.alkemy.ong.util.DocumentationResponse.ADMIN;
import static com.alkemy.ong.util.DocumentationResponse.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Mock
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    private UserDto dto;
    private List<UserDto> dtos;
    private List<UserDto> dtosEmpty;


    @InjectMocks
    private UserController userController;
   @Spy
   private final  UserMapper userMapper = new UserMapperImpl();
    @BeforeEach
    void setUp() {
        RoleEntity roleId = new RoleEntity();
        dto = new UserDto();
        dto.setId("1L");
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setEmail("email");
        dto.setPassword("password");
        dto.setRoleId(roleId);
        dto.setTimestamps(LocalDateTime.now());
        dtos = new ArrayList<>();
        dtos.add(dto);
        dtosEmpty = null;


    }
    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

    @Test
    @WithUserDetails(ADMIN)
    void  TestGetUserList_ForAdmin() throws Exception {
        Mockito.when(userService.getAllUser()).thenReturn(dtos);
        mockMvc.perform(get("/users/list")).andExpect(status().isOk());

    }@Test
    @WithUserDetails(USER)
    void  TestGetUserList_ForUser() throws Exception {
        Mockito.when(userService.getAllUser()).thenReturn(dtos);
        mockMvc.perform(get("/users/list")).andExpect(status().isForbidden());

    }


  /*  @Test
    @WithUserDetails(ADMIN)
    void  TestGetUserList_ForAdmin_With_Empty_List() throws Exception {
        Mockito.when(userService.getAllUser()).thenReturn(dtos);
        mockMvc.perform(get("/users/list")).andExpect(status().isNoContent());
    }*/

//    @Test
//    void userMe() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void update() {
//    }
}