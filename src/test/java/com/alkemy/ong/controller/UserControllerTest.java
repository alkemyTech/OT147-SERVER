package com.alkemy.ong.controller;


import com.alkemy.ong.auth.dto.UserDTO;
import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.dto.UserDto;
import com.alkemy.ong.entity.RoleEntity;
import com.alkemy.ong.entity.TestimonialEntity;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.exceptions.ParamNotFound;
import com.alkemy.ong.mapper.UserMapper;
import com.alkemy.ong.mapper.UserMapperImpl;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.stubbing.BaseStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.alkemy.ong.util.DocumentationResponse.ADMIN;
import static com.alkemy.ong.util.DocumentationResponse.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();

    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtUtils jwtUtils;
    @Autowired
    private MockMvc mockMvc;

    private UserDto dto;
    private RoleEntity roleId;
    private List<UserDto> dtos;
    private String jwtUser;
    private String jwtAdmin;
    private UserEntity userEntity;
    private UserEntity userEntityTo;

    @InjectMocks
    private UserController userController;
    @Spy
    private final UserMapper userMapper = new UserMapperImpl();


    @BeforeEach
    void setUp() {
        roleId = new RoleEntity();
        roleId.setId("1L");
        roleId.setName("ROLE_USER");
        roleId.setDescription("ROLE_USER");


        dto = new UserDto();
        dto.setId("1L");
        dto.setFirstName("firstName");
        dto.setLastName("lastName");
        dto.setEmail("email");
        dto.setPassword("password");
        dto.setRoleId(roleId);

        dtos = new ArrayList<>();
        dtos.add(dto);

        List<UserDto> dtosNull = null;

        userEntity = new UserEntity();
        userEntity.setId("123456");
        userEntity.setFirstName("firstName");
        userEntity.setLastName("lastName");
        userEntity.setEmail("email");
        userEntity.setPassword("password");
        userEntity.setRoleId(roleId);
        userEntity.setTimestamps(LocalDateTime.of(1, 1, 1, 1, 1));
        userEntity.setSoftDelete(false);

        jwtUser = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbWFjZ2lsbHJlaWNoYUBzYndpcmUuY29tIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE2NDc4MzUzMjQsImV4cCI6MTY0Nzg3MTMyNH0.doLN_3xbMtyyGTTvx4OP_YeegaZGw_hH8_gK0quFJJc";
        jwtAdmin = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZ2FkZXNieTFAbnN3Lmdvdi5hdSIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY0NzgzNTk0MCwiZXhwIjoxNjQ3ODcxOTQwfQ.Y7ha79y7TIRJuLe55r1PAJ3ISbtTR9ACHRm96EobK68";


    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    @WithUserDetails(ADMIN)
    void TestGetUserList_ForAdmin_Ok() throws Exception {
        Mockito.when(userService.getAllUser()).thenReturn(dtos);
        RequestBuilder requestBuilder = get("/users/list").accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(get("/users/list"))
                .andExpect(status().isOk());

    }

    @Test
    @WithUserDetails(USER)
    void TestGetUserList_ForUser_Forbidden() throws Exception {
        Mockito.when(userService.getAllUser()).thenReturn(dtos);
        mockMvc.perform(get("/users/list"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(USER)
    void TestGetUserMe_Data_ForUser_Ok() throws Exception {
        Mockito.when(userService.userMe(jwtUser)).thenReturn(dto);
        RequestBuilder requestBuilder = get("/users/auth/me")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtUser);
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(ADMIN)
    void TestGetUserMe_Data_ForAdmin_Forbidden() throws Exception {
        Mockito.when(userService.userMe(jwtAdmin)).thenReturn(dto);
        RequestBuilder requestBuilder = get("/users/auth/me")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtUser);
        mockMvc.perform(requestBuilder).andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(USER)
    void TestDeleteUserById_User() throws Exception {
        Mockito.when(userRepository
                .save(userEntity)).thenReturn(userEntity);
        UserEntity userEntityTo = new UserEntity();
        userEntityTo.setId("123456");
        userEntityTo.setSoftDelete(true);
        Mockito.when(userRepository
                        .findById(Mockito.anyString()))
                .thenReturn(Optional.of(userEntity));
        Mockito.when(userRepository
                .save(userEntityTo)).thenReturn(userEntityTo);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/users/123456").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(USER)
    void testDelete_IsNotFound() throws Exception {
        doThrow(new ParamNotFound("An error occurred")).when(this.userService).deleteUserById((String) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{id}", "42");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithUserDetails(ADMIN)
    void TestDeleteUserById_Admin_Forbidden() throws Exception {
        Mockito.when(userRepository
                .save(userEntity)).thenReturn(userEntity);
        UserEntity userEntityTo = new UserEntity();
        userEntityTo.setId("123456");
        userEntityTo.setSoftDelete(true);
        Mockito.when(userRepository
                        .findById(Mockito.anyString()))
                .thenReturn(Optional.of(userEntity));
        Mockito.when(userRepository
                .save(userEntityTo)).thenReturn(userEntityTo);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/users/123456").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isForbidden());
    }


   @Test
    @WithUserDetails(USER)
    void testUpdateUserById_VerbPatch() throws Exception {

        UserDto dtoUpdate = new UserDto();
        dtoUpdate.setId("123456");
        dtoUpdate.setFirstName("lola");
        dtoUpdate.setLastName("bunny");
        dtoUpdate.setEmail("demo@email.com");
        dtoUpdate.setPhoto("default.jpg");

        Mockito.when(userRepository
                .save(userEntity)).thenReturn(userEntity);
        UserEntity userEntityUpdate = new UserEntity();
        userEntityUpdate.setId("123456");
        userEntityUpdate.setFirstName("lola");
        userEntityUpdate.setLastName("bunny");
        userEntityUpdate.setEmail("demo@email.com");
        userEntityUpdate.setPhoto("default.jpg");
        Mockito.when(userRepository
                        .findById(Mockito.anyString()))
                .thenReturn(Optional.of(userEntity));
        Mockito.when(userRepository
                .save(userEntityUpdate)).thenReturn(userEntityUpdate);
            String updatedContent=objectWriter.writeValueAsString(userEntityUpdate);
            MockHttpServletRequestBuilder mockHttpRequest = patch("/users/123456",dtoUpdate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(updatedContent);
            mockMvc.perform(mockHttpRequest).andExpect(status().isOk());

    }

    @Test
    @WithUserDetails(USER)
    void testUpdateUserById_VerbPatchIsNotFound() throws Exception {
        doThrow(new ParamNotFound("An error occurred")).when(this.userService).updateUser((String) any(),(UserDto) any());

        UserDto dtoUpdate = new UserDto();
        dtoUpdate.setFirstName("lola");
        dtoUpdate.setLastName("bunny");
        dtoUpdate.setEmail("demo@email.com");
        dtoUpdate.setPhoto("default.jpg");

        UserEntity userEntityUpdate = new UserEntity();
        userEntityUpdate.setId("123456");
        userEntityUpdate.setFirstName("lola");
        userEntityUpdate.setLastName("bunny");
        userEntityUpdate.setEmail("demo@email.com");
        userEntityUpdate.setPhoto("default.jpg");
        String updatedContent=objectWriter.writeValueAsString(userEntityUpdate);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/users/{id}", "42",dtoUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(requestBuilder).andExpect(status().isNotFound());

    }

    @Test
    @WithUserDetails(ADMIN)
    void testUpdateAdminById_VerbForbidden() throws Exception {
        doThrow(new ParamNotFound("An error occurred")).when(this.userService).updateUser((String) any(),(UserDto) any());

        UserDto dtoUpdate = new UserDto();
        dtoUpdate.setFirstName("lola");
        dtoUpdate.setLastName("bunny");
        dtoUpdate.setEmail("demo@email.com");
        dtoUpdate.setPhoto("default.jpg");

        UserEntity userEntityUpdate = new UserEntity();
        userEntityUpdate.setId("123456");
        userEntityUpdate.setFirstName("lola");
        userEntityUpdate.setLastName("bunny");
        userEntityUpdate.setEmail("demo@email.com");
        userEntityUpdate.setPhoto("default.jpg");
        String updatedContent=objectWriter.writeValueAsString(userEntityUpdate);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/users/{id}", "42",dtoUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(requestBuilder).andExpect(status().isForbidden());

    }

}
