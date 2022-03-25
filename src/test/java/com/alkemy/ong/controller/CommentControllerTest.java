package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentBodyDto;
import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.entity.NewsEntity;
import com.alkemy.ong.entity.UserEntity;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.mapper.CommentMapperImpl;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UserRepository;
import com.alkemy.ong.service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static com.alkemy.ong.util.DocumentationResponse.ADMIN;
import static com.alkemy.ong.util.DocumentationResponse.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerTest {

    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();

    private CommentBodyDto bodyDTO1;
    private CommentBodyDto bodyDTO2;
    private CommentDto dto1;
    private CommentDto dto2;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private NewsRepository newRepository;
    @Mock
    private UserRepository userRepository;
    @MockBean
    private CommentService commentService;
    @MockBean
    private Authentication auth;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserEntity user;
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private CommentController commentController;
    @Spy
    private final CommentMapper mapper=new CommentMapperImpl();

    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.objectMapper = new ObjectMapper();

        NewsEntity newsEntity=new NewsEntity();
        newsEntity.setId("12234324");
        newsEntity.setName("Name");
        newsEntity.setContent("Content");
        newRepository.save(newsEntity);

        UserEntity user=new UserEntity();
        user.setId("12234324");
        user.setEmail("Name@gmail.com");
        user.setFirstName("Content");
        user.setLastName("Pepe");
        user.setPassword("12232334");
        userRepository.save(user);

        bodyDTO1 = new CommentBodyDto("body");
        bodyDTO2 = new CommentBodyDto("body");
        dto1 = new CommentDto();
        dto1.setNewsId(newsEntity);
        dto1.setUserId(user);
        dto1.setBody("body");
        dto2 = new CommentDto();
        dto2.setNewsId(newsEntity);
        dto2.setUserId(user);
        dto2.setBody("body");
    }

    // Get
    @Test
    void getNotLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/comments"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithUserDetails(USER)
    void getByUserForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/comments"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN)
    public void getOk() throws Exception {
        Mockito.when(commentService.getAll()).thenReturn(Arrays.asList(bodyDTO1, bodyDTO2));
        mockMvc.perform(MockMvcRequestBuilders.get("/comments"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findAllNotLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/comments/posts" + "/{id}" + "/comments", "12234324"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithUserDetails(USER)
    void findAllByUser() throws Exception {
        Mockito.when(commentService.getCommentsByNewsId("12234324")).thenReturn(Arrays.asList(dto1, dto2));
        mockMvc.perform(MockMvcRequestBuilders.get("/comments/posts" + "/{id}" + "/comments", "12234324"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN)
    void findAllByAdmin() throws Exception {
        Mockito.when(commentService.getCommentsByNewsId("12234324")).thenReturn(Arrays.asList(dto1, dto2));
        mockMvc.perform(MockMvcRequestBuilders.get("/comments/posts" + "/{id}" + "/comments", "12234324"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // Post

    @Test
    @WithUserDetails(USER)
    void postCommentController_save_Create_RoleUSER() throws Exception {
        String content=objectWriter.writeValueAsString(dto1);
        Mockito.when(commentService.save(dto1)).thenReturn(dto1);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void postNotLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/comments"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    @Test
    @WithUserDetails(ADMIN)
    void postCommentController_saveComment_Create_Role_ADMIN_UNPROCESSABLE_ENTITY() throws Exception {
        when(commentController.create(dto1)).thenThrow(new NullPointerException());
        commentController.create(dto1);
    }

    //Put
    @Test
    @WithMockUser(ADMIN)
    void TestPutSlideController_ADMIN_Not_Found() throws Exception {
        user = createUser();
        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        when(commentController.updateComment(auth,dto1,"123")).thenThrow(new NullPointerException());
        commentController.updateComment(auth,dto1,"123");
    }
    @Test
    void PutNotLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/comments" + "/{id}", "abc"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN)
    void putCommentExceptionBadRequest() throws Exception {
        Mockito.when(commentService.existId("12234324")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.put("/comments" + "/{id}", "12234324")
                        .content(getJSON(dto1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Delete

    @Test
    void deleteNotLoggedIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/comments" + "/{id}", "abc"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithUserDetails(USER)
    void deleteByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/comments" + "/{id}", "abc"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
    @Test
    @WithMockUser(roles = "CLIENT")
    void deleteByRoleForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/comments" + "/{id}", "abc"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(ADMIN)
    void deleteExceptionNotFound() throws Exception {
        user = new UserEntity();
        user.setFirstName("Martin");
        user.setLastName("Lora");
        user.setEmail("martin@martin.com");
        user.setPassword("12345678");

        auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        when(commentController.delete(auth,"123")).thenThrow(new NullPointerException());
        commentController.delete(auth,"123");
    }

    @Test
    @WithUserDetails(ADMIN)
    void deleteCommentException() throws Exception {
        Mockito.when(commentService.existId("12234324")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/comments" + "/{id}", "12234324")
                        .content(getJSON(dto1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private String getJSON(CommentDto commentDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(commentDTO);
    }

    private UserEntity createUser(){
        UserEntity user = new UserEntity();
        user.setFirstName("Martin");
        user.setLastName("Lora");
        user.setEmail("martin@martin.com");
        user.setPassword("12345678");
        return user;
    }

}