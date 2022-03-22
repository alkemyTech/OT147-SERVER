package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.entity.CommentEntity;
import com.alkemy.ong.entity.NewsEntity;
import com.alkemy.ong.mapper.CommentMapper;
import com.alkemy.ong.mapper.CommentMapperImpl;
import com.alkemy.ong.mapper.NewsMapper;
import com.alkemy.ong.mapper.NewsMapperImpl;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.CommentService;
import com.alkemy.ong.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.alkemy.ong.util.DocumentationResponse.ADMIN;
import static com.alkemy.ong.util.DocumentationResponse.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
public class NewsControllerTest {
    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();
    @Mock
    private NewsRepository newsRepository;
    @MockBean
    private NewsService newsService;
    @MockBean
    private CommentService commentService;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private NewsController newsController;
    @Spy
    private final NewsMapper mapper=new NewsMapperImpl();
    @Spy
    private final CommentMapper mapperComment=new CommentMapperImpl();
    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestGetNewsController_readNews_Create_Role_ADMIN() throws Exception {
        NewsEntity newsEntity = createNewsEntity();
        NewsDto dto=mapper.newsEntityToNewsDto(newsEntity);
        when(newsService.getDetailsById("123e4567-e89b-12d3-a456-426614174200")).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.get("/news/123e4567-e89b-12d3-a456-426614174200")
                     .contentType(MediaType.APPLICATION_JSON))
                     .andExpect(status().isOk());
    }
    @Test
    @WithUserDetails(USER)
    void TestGetNewsController_ReadCommmentByNewsId_Create_Role_USER() throws Exception {
        CommentEntity comment= new CommentEntity();
        NewsEntity newsEntity = createNewsEntity();
        comment.setNewsId(newsEntity);
        comment.setId("123456");
        comment.setBody("Body");
        comment.setUserId(null);
        comment.setSoftDelete(Boolean.FALSE);
        CommentDto commentDto= mapperComment.commentEntityToCommentDto(comment);
        List<CommentDto> listCommentDto= new ArrayList<>(Arrays.asList(commentDto));
        System.out.println(listCommentDto.toString());
        when(commentService.getCommentsByNewsId("123e4567-e89b-12d3-a456-426614174200")).thenReturn(listCommentDto);
        System.out.println(commentService.getCommentsByNewsId("123e4567-e89b-12d3-a456-426614174200").toString());
        mockMvc.perform(MockMvcRequestBuilders.get("/news/123e4567-e89b-12d3-a456-426614174200/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPostNewsController_saveNews_Create_Role_ADMIN() throws Exception{
        NewsEntity newsEntity = createNewsEntity();
        NewsDto dto=mapper.newsEntityToNewsDto(newsEntity);
        String content=objectWriter.writeValueAsString(dto);
        when(newsService.save(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPostNewsController_saveNews_Create_Role_ADMIN_UNPROCESSABLE_ENTITY() throws Exception {
        NewsEntity news= new NewsEntity();
        NewsDto dto=mapper.newsEntityToNewsDto(news);
        when(newsController.create(dto)).thenThrow(new NullPointerException());
        newsController.create(dto);
    }
    @Test
    @WithUserDetails(USER)
    void TestPostNewsController_saveNews_Create_Role_USER_Forbidden() throws Exception{
        NewsEntity newsEntity = createNewsEntity();
        NewsDto dto=mapper.newsEntityToNewsDto(newsEntity);
        String content=objectWriter.writeValueAsString(dto);
        when(newsService.save(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithMockUser(roles = "CLIENT")
    void TestPostNewsController_saveNews_Create_Role__Forbidden() throws Exception{
        NewsEntity newsEntity = createNewsEntity();
        NewsDto dto=mapper.newsEntityToNewsDto(newsEntity);
        String content=objectWriter.writeValueAsString(dto);
        when(newsService.save(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPostNewsController_validate_Create_Role_ADMIN_BAD_REQUEST_Name() throws Exception {
        NewsDto news= new NewsDto();
        CategoryEntity category = new CategoryEntity();
        category.setId("1234");
        category.setImage("Imagen category");
        category.setName("nombre category");
        category.setSoftDelete(false);
        category.setDescription("Description");
        news.setName("");
        news.setContent("Contenido");
        news.setImage("Imagen");
        news.setCategoryId(category);
        String content=objectWriter.writeValueAsString(news);
        when(newsService.save(news)).thenReturn(news);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPostNewsController_validate_Create_Role_ADMIN_BAD_REQUEST_Content() throws Exception {
        NewsDto news= new NewsDto();
        CategoryEntity category = new CategoryEntity();
        category.setId("1234");
        category.setImage("Imagen category");
        category.setName("nombre category");
        category.setSoftDelete(false);
        category.setDescription("Description");
        news.setName("Novedad");
        news.setContent("");
        news.setImage("Imagen");
        news.setCategoryId(category);
        String content=objectWriter.writeValueAsString(news);
        when(newsService.save(news)).thenReturn(news);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPostNewsController_validate_Create_Role_ADMIN_BAD_REQUEST_Image() throws Exception {
        NewsDto news= new NewsDto();
        CategoryEntity category = new CategoryEntity();
        category.setId("1234");
        category.setImage("Imagen category");
        category.setName("nombre category");
        category.setSoftDelete(false);
        category.setDescription("Description");
        news.setName("Novedad");
        news.setContent("Contenido");
        news.setImage("");
        news.setCategoryId(category);
        String content=objectWriter.writeValueAsString(news);
        when(newsService.save(news)).thenReturn(news);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPostNewsController_validate_Create_Role_ADMIN_BAD_REQUEST_Category() throws Exception {
        NewsDto news= new NewsDto();
        news.setName("Novedad");
        news.setContent("Contenido");
        news.setImage("Image");
        news.setCategoryId(null);
        String content=objectWriter.writeValueAsString(news);
        when(newsService.save(news)).thenReturn(news);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPutNewsController_updateNews_Create_Role_ADMIN() throws Exception {
            NewsEntity newsEntity = createNewsEntity();
            when(newsRepository.save(newsEntity)).thenReturn(newsEntity);
            NewsEntity newsEntity2 = createNewsEntity2();
            when(newsRepository.findById(newsEntity.getId())).thenReturn(java.util.Optional.of(newsEntity));
            when(newsRepository.save(newsEntity2)).thenReturn(newsEntity2);
            String updatedContent=objectWriter.writeValueAsString(newsEntity2);
            MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/news/123e4567-e89b-12d3-a456-426614174000")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(updatedContent);
            mockMvc.perform(mockRequest)
                    .andExpect(status().isOk());
    }
    @Test
    @WithUserDetails(USER)
    void TestPutNewsController_updateNews_Create_Role_USER() throws Exception {
        NewsEntity newsEntity = createNewsEntity();
        when(newsRepository.save(newsEntity)).thenReturn(newsEntity);
        NewsEntity newsEntity2 = createNewsEntity2();
        when(newsRepository.findById(newsEntity.getId())).thenReturn(java.util.Optional.of(newsEntity));
        when(newsRepository.save(newsEntity2)).thenReturn(newsEntity2);
        String updatedContent=objectWriter.writeValueAsString(newsEntity2);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/news/123e4567-e89b-12d3-a456-426614174000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(mockRequest)
                .andExpect(status().isForbidden());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestDeleteNewsController_updateNews_Create_Role_ADMIN_Not_Found() throws Exception {
        NewsDto newsDto= new NewsDto();
        Throwable thrown = assertThrows(Exception.class, () -> newsController.update("1233",newsDto));
        assertEquals("There are no news with the provided News id", thrown.getMessage());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestDeleteNewsController_eliminateNews_Create_Role_ADMIN() throws Exception {
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.delete("/news/987")
                    .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                    .andExpect(status().isNoContent());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestDeleteNewsController_eliminateNews_Create_Role_ADMIN_Not_Found() throws Exception {
        Throwable thrown = assertThrows(Exception.class, () -> newsController.delete("100"));
        assertEquals("There are no news with the provided News id", thrown.getMessage());
    }
    @Test
    @WithUserDetails(USER)
    void TestGetNews_ValidatePaginationOk_Role_USER() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/news".concat("?page=")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestGetNews_ValidatePaginationOk_Role_ADMIN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/news".concat("?page=")))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    private NewsEntity createNewsEntity(){
        CategoryEntity category = new CategoryEntity();
        category.setId("1234");
        category.setImage("Imagen category");
        category.setName("nombre category");
        category.setSoftDelete(false);
        category.setDescription("Description");
        NewsEntity news = new NewsEntity();
        news.setId("123e4567-e89b-12d3-a456-426614174200");
        news.setName("Novedad");
        news.setContent("Contenido");
        news.setImage("Imagen");
        news.setCategoryId(category);
        news.setSoftDelete(false);
        return news;
    }
    private NewsEntity createNewsEntity2(){
        CategoryEntity category = new CategoryEntity();
        category.setId("1234");
        category.setImage("Imagen category2");
        category.setName("nombre category2");
        category.setSoftDelete(false);
        category.setDescription("Description2");
        NewsEntity news = new NewsEntity();
        news.setId("123e4567-e89b-12d3-a456-426614174000");
        news.setName("Novedad2");
        news.setContent("Contenido2");
        news.setImage("Imagen2");
        news.setCategoryId(category);
        news.setSoftDelete(false);
        return news;
    }
}
