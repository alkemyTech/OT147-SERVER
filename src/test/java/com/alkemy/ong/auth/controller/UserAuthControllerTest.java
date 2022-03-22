package com.alkemy.ong.auth.controller;

import com.alkemy.ong.auth.dto.AuthRequestDTO;
import com.alkemy.ong.auth.service.JwtUtils;
import com.alkemy.ong.auth.service.UserDetailsCustomService;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@AutoConfigureMockMvc
class UserAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserDetailsCustomService userService;

    @MockBean
    private Authentication auth;

    @MockBean
    private JwtUtils jwtTokenUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    private static final String EMAIL = "email@email.com";

    private UserEntity user;

    ObjectMapper objectMapper=new ObjectMapper();

    ObjectWriter objectWriter=objectMapper.writer();

    @Autowired
    UserAuthController userAuthController;

    @MockBean
    BCryptPasswordEncoder encoder;

    private AuthRequestDTO dto;

    protected String getObject(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
    protected String path = "/auth";


    @Test
    @Transactional
    void PostRegister_UserVALID_isOK() throws Exception {
        user=createUser();
        userRepository.save(user);
        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String jwt = jwtTokenUtil.generateToken(auth);
        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "register"))
                        .content(getObject(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    void PostRegister_UserINVALID_FirstName_isBadRequest() throws Exception {
        user=createUser();
        user.setFirstName("");
        userRepository.save(user);
        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String jwt = jwtTokenUtil.generateToken(auth);
        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "register"))
                        .content(getObject(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @Transactional
    void PostRegister_UserINVALID_LastName_isBadRequest() throws Exception {
        user=createUser();
        user.setLastName("");
        userRepository.save(user);
        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String jwt = jwtTokenUtil.generateToken(auth);
        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "register"))
                        .content(getObject(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @Transactional
    void PostRegister_UserINVALID_EmailNull_isBadRequest() throws Exception {
        user=createUser();
        user.setEmail(null);
        userRepository.save(user);
        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String jwt = jwtTokenUtil.generateToken(auth);
        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "register"))
                        .content(getObject(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    void PostRegister_UserINVALID_PasswordSizeMin8characters_isBadRequest() throws Exception {
        user=createUser();
        user.setPassword("12344");
        userRepository.save(user);
        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String jwt = jwtTokenUtil.generateToken(auth);
        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "register"))
                        .content(getObject(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @Transactional
    void PostRegister_UserINVALID_PasswordEmpty_isBadRequest() throws Exception {
        user=createUser();
        user.setPassword("");
        userRepository.save(user);
        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String jwt = jwtTokenUtil.generateToken(auth);
        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "register"))
                        .content(getObject(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @Transactional
    void PostRegister_UserINVALID_PasswordNull_isBadRequest() throws Exception {
        user=createUser();
        user.setPassword(null);
        userRepository.save(user);
        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String jwt = jwtTokenUtil.generateToken(auth);
        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "register"))
                        .content(getObject(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void PostLogin_CredentialsVALID_isOK() throws Exception {
        user=createUser();
        userRepository.save(user);
        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        String jwt = jwtTokenUtil.generateToken(auth);

        dto = new AuthRequestDTO();
        dto.setUsername(EMAIL);
        dto.setPassword("12345678");

        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        String jwT = jwtTokenUtil.generateToken(auth);
        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "signin"))
                        .content(getObject(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void PostLogin_CredentialsINVALID_isBadRequest() throws Exception {
        dto = new AuthRequestDTO();
        dto.setUsername("EMAIL");
        dto.setPassword("12345");
        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        String jwT = jwtTokenUtil.generateToken(auth);
        mockMvc
                .perform(MockMvcRequestBuilders.post(String.format("%s/%s", path, "signin"))
                        .content(getObject(user)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    private UserEntity createUser(){
        user = new UserEntity();
        user.setFirstName("Martin");
        user.setLastName("Lora");
        user.setEmail(EMAIL);
        user.setPassword("12345678");

        return user;
    }



}