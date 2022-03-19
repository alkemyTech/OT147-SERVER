package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.entity.TestimonialEntity;
import com.alkemy.ong.mapper.TestimonialMapper;
import com.alkemy.ong.mapper.TestimonialMapperImpl;
import com.alkemy.ong.repository.TestimonyRepository;
import com.alkemy.ong.service.TestimonialService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.*;
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

import static com.alkemy.ong.util.DocumentationResponse.ADMIN;
import static com.alkemy.ong.util.DocumentationResponse.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
class TestimonialControllerTest {

    ObjectMapper objectMapper=new ObjectMapper();

    ObjectWriter objectWriter=objectMapper.writer();

    @Mock
    private TestimonyRepository testimonyRepository;
    @MockBean
    private TestimonialService testimonialService;
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TestimonialController testimonialController;
    @Spy
    private final TestimonialMapper mapper=new TestimonialMapperImpl();

    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPostTestimonialController_saveTestimonial_Create_Role_ADMIN() throws Exception {
        TestimonialEntity testimonialEntity = createTestimonialEntity();
        TestimonialDto dto=mapper.testimonialToTestimonialDto(testimonialEntity);
        String content=objectWriter.writeValueAsString(dto);
        Mockito.when(testimonialService.saveTestimonial(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/testimonials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithUserDetails(USER)
    void TestPostTestimonialController_saveTestimonial_Create_Role_USER_Forbidden() throws Exception {
        TestimonialEntity testimonialEntity = createTestimonialEntity();
        TestimonialDto dto=mapper.testimonialToTestimonialDto(testimonialEntity);
        String content=objectWriter.writeValueAsString(dto);
        Mockito.when(testimonialService.saveTestimonial(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/testimonials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void TestPostTestimonialController_saveTestimonial_Create_Role_CLIENT_Forbidden() throws Exception {
        TestimonialEntity testimonialEntity = createTestimonialEntity();
        TestimonialDto dto=mapper.testimonialToTestimonialDto(testimonialEntity);
        String content=objectWriter.writeValueAsString(dto);
        Mockito.when(testimonialService.saveTestimonial(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/testimonials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails(ADMIN)
    void TestPostTestimonialController_validate_Create_Role_ADMIN_BAD_REQUEST_Name() throws Exception {
        TestimonialDto dto=new TestimonialDto();
        dto.setName("  ");
        dto.setContent("Hello");
        dto.setImage("img.jpg");
        String content=objectWriter.writeValueAsString(dto);
        Mockito.when(testimonialService.saveTestimonial(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/testimonials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPostTestimonialController_validate_Create_Role_ADMIN_BAD_REQUEST_Content() throws Exception {
        TestimonialDto dto=new TestimonialDto();
        dto.setName("Pepe");
        dto.setContent("");
        dto.setImage("img.jpg");
        String content=objectWriter.writeValueAsString(dto);
        Mockito.when(testimonialService.saveTestimonial(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/testimonials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPostTestimonialController_validate_Create_Role_ADMIN_BAD_REQUEST_Content_and_Name() throws Exception {
        TestimonialDto dto=new TestimonialDto();
        dto.setName("");
        dto.setContent("");
        dto.setImage("img.jpg");
        String content=objectWriter.writeValueAsString(dto);
        Mockito.when(testimonialService.saveTestimonial(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/testimonials")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithUserDetails(ADMIN)
    void TestPutTestimonialController_updateTestimonial_Create_Role_ADMIN() throws Exception {
        TestimonialEntity testimonialEntity = createTestimonialEntity();
        Mockito.when(testimonyRepository.save(testimonialEntity)).thenReturn(testimonialEntity);

        TestimonialEntity testimonialEntity2 = createTestimonialEntityII();
        Mockito.when(testimonyRepository.findById(testimonialEntity.getId())).thenReturn(java.util.Optional.of(testimonialEntity));
        Mockito.when(testimonyRepository.save(testimonialEntity2)).thenReturn(testimonialEntity2);
        String updatedContent=objectWriter.writeValueAsString(testimonialEntity2);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/testimonials/123456")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(USER)
    void TestPutTestimonialController_updateTestimonial_Role_USER_Forbidden() throws Exception {
        TestimonialEntity testimonialEntity = createTestimonialEntity();
        Mockito.when(testimonyRepository.save(testimonialEntity)).thenReturn(testimonialEntity);

        TestimonialEntity testimonialEntity2 = createTestimonialEntityII();

        Mockito.when(testimonyRepository.findById(testimonialEntity.getId())).thenReturn(java.util.Optional.of(testimonialEntity));
        Mockito.when(testimonyRepository.save(testimonialEntity2)).thenReturn(testimonialEntity2);
        String updatedContent=objectWriter.writeValueAsString(testimonialEntity2);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/testimonials/123456")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(mockRequest)
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN)
    void TestPutTestimonialController_updateTestimonial_Role_ADMIN_validateName() throws Exception {
        TestimonialEntity testimonialEntity = createTestimonialEntity();
        Mockito.when(testimonyRepository.save(testimonialEntity)).thenReturn(testimonialEntity);
        TestimonialEntity testimonialEntity2 = new TestimonialEntity();
        testimonialEntity2.setId("123456");
        testimonialEntity2.setImage("Img.jpg");
        testimonialEntity2.setName("");
        testimonialEntity2.setSoftDelete(false);
        testimonialEntity2.setContent("Content");
        Mockito.when(testimonyRepository.findById(testimonialEntity.getId())).thenReturn(java.util.Optional.of(testimonialEntity));
        Mockito.when(testimonyRepository.save(testimonialEntity2)).thenReturn(testimonialEntity2);
        String updatedContent=objectWriter.writeValueAsString(testimonialEntity2);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/testimonials/123456")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(ADMIN)
    void TestPutTestimonialController_updateTestimonial_Role_ADMIN_validateContent() throws Exception {
        TestimonialEntity testimonialEntity = createTestimonialEntity();
        Mockito.when(testimonyRepository.save(testimonialEntity)).thenReturn(testimonialEntity);
        TestimonialEntity testimonialEntity2 = new TestimonialEntity();
        testimonialEntity2.setId("123456");
        testimonialEntity2.setImage("Img.jpg");
        testimonialEntity2.setName("Name complete");
        testimonialEntity2.setSoftDelete(false);
        testimonialEntity2.setContent("");
        Mockito.when(testimonyRepository.findById(testimonialEntity.getId())).thenReturn(java.util.Optional.of(testimonialEntity));
        Mockito.when(testimonyRepository.save(testimonialEntity2)).thenReturn(testimonialEntity2);
        String updatedContent=objectWriter.writeValueAsString(testimonialEntity2);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/testimonials/123456")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestPutTestimonialController_updateTestimonial_Role_ADMIN_validate_ContentAndName() throws Exception {
        TestimonialEntity testimonialEntity = createTestimonialEntity();
        Mockito.when(testimonyRepository.save(testimonialEntity)).thenReturn(testimonialEntity);
        TestimonialEntity testimonialEntity2 = new TestimonialEntity();
        testimonialEntity2.setId("123456");
        testimonialEntity2.setImage("Img.jpg");
        testimonialEntity2.setName("");
        testimonialEntity2.setSoftDelete(false);
        testimonialEntity2.setContent("");
        Mockito.when(testimonyRepository.findById(testimonialEntity.getId())).thenReturn(java.util.Optional.of(testimonialEntity));
        Mockito.when(testimonyRepository.save(testimonialEntity2)).thenReturn(testimonialEntity2);
        String updatedContent=objectWriter.writeValueAsString(testimonialEntity2);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/testimonials/123456")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(ADMIN)
    void TestDeleteTestimonialController_eliminateTestimonial_Create_Role_ADMIN() throws Exception {
        TestimonialEntity testimonialEntity = createTestimonialEntity();
        Mockito.when(testimonyRepository.save(testimonialEntity)).thenReturn(testimonialEntity);
        TestimonialEntity testimonialEntity2 = new TestimonialEntity();
        testimonialEntity2.setId("123456");
        testimonialEntity2.setImage("Img.jpg");
        testimonialEntity2.setName("name testimonial");
        testimonialEntity2.setSoftDelete(true);
        testimonialEntity2.setContent("Content");
        Mockito.when(testimonyRepository.findById(testimonialEntity.getId())).thenReturn(java.util.Optional.of(testimonialEntity));
        Mockito.when(testimonyRepository.save(testimonialEntity2)).thenReturn(testimonialEntity2);
        String updatedContent=objectWriter.writeValueAsString(testimonialEntity2);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.delete("/testimonials/123456")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());
    }
    @Test
    @WithUserDetails(USER)
    void TestGetTestimonial_ValidatePaginationOk_Role_USER() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/testimonials".concat("?page=")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @WithUserDetails(ADMIN)
    void TestGetTestimonial_ValidatePaginationForbidden_Role_ADMIN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/testimonials".concat("?page=")))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    
    private TestimonialEntity createTestimonialEntity(){
        TestimonialEntity testimonialEntity = new TestimonialEntity();
        testimonialEntity.setId("123456");
        testimonialEntity.setImage("Img.jpg");
        testimonialEntity.setName("name");
        testimonialEntity.setSoftDelete(false);
        testimonialEntity.setContent("Content");
        return testimonialEntity;
    }
    private TestimonialEntity createTestimonialEntityII(){
        TestimonialEntity testimonialEntity2 = new TestimonialEntity();
        testimonialEntity2.setId("123456");
        testimonialEntity2.setImage("Img.jpg");
        testimonialEntity2.setName("name testimonial");
        testimonialEntity2.setSoftDelete(false);
        testimonialEntity2.setContent("Content");
        return testimonialEntity2;
    }

}


