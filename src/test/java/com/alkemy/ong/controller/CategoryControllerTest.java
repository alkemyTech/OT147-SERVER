package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryDto;
import com.alkemy.ong.dto.CategoryDtoFull;
import com.alkemy.ong.entity.CategoryEntity;
import com.alkemy.ong.mapper.CategoryMapper;
import com.alkemy.ong.mapper.CategoryMapperImpl;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc()
@AutoConfigureJsonTesters
@SpringBootTest
class CategoryControllerTest {

    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();
    @Mock
    private CategoryRepository categoryRepository;
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private CategoryController categoryController;
    @Spy
    private final CategoryMapper categoryMapper=new CategoryMapperImpl();
    // private CategoryEntity savedCategory;
    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }
    // Admin POST Activity -> Ok
    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void createCategory_statusOK() throws Exception {
//      Model de CategoryDto
        CategoryDto categoryDTO=new CategoryDto();
        categoryDTO.setImage("12");
        categoryDTO.setName("A name");
        categoryDTO.setDescription("A valid content");
        when(categoryService.addCategory(categoryDTO)).thenReturn(categoryDTO);
        String content=objectWriter.writeValueAsString(categoryDTO);
        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void whenValidInput_thenReturns204() throws Exception {
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.delete("/categories/123456")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    public void testUpdateIsNotFound() {
        Throwable thrown = assertThrows(Exception.class, () -> categoryController.delete("100"));
        assertEquals(null, thrown.getMessage());
    }
    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void TestGetCategories_ValidatePaginationOk_Role_ADMIN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories" .concat("?page=")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void updateWithSuccess() throws Exception {
        CategoryEntity cate = new CategoryEntity();
        cate.setId("123456");
        cate.setImage("Image category");
        cate.setName("Name category");
        cate.setSoftDelete(false);
        cate.setDescription("Description");

        CategoryEntity cateUpdate = new CategoryEntity();
        cateUpdate.setId("123456");
        cateUpdate.setImage("IMAGE");
        cateUpdate.setName("Name category");
        cateUpdate.setSoftDelete(false);
        cateUpdate.setDescription("Description");

        Mockito.when(categoryRepository.findById(cate.getId())).thenReturn(java.util.Optional.of(cate));
        Mockito.when(categoryRepository.save(cateUpdate)).thenReturn(cateUpdate);
        String updatedContent=objectWriter.writeValueAsString(cateUpdate);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/categories/123456")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void getCategory() throws Exception {
        CategoryDtoFull cate = new CategoryDtoFull();
        cate.setId("123456");
        cate.setImage("Image category");
        cate.setName("Name category");
        cate.setSoftDelete(false);
        cate.setDescription("Description");
        when(categoryService.getCategory("123456")).thenReturn(cate);
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}