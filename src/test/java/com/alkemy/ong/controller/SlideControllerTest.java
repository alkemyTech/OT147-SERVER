package com.alkemy.ong.controller;
import com.alkemy.ong.dto.SlideBasicDto;
import com.alkemy.ong.dto.SlideDtoFull;
import com.alkemy.ong.entity.OrganizationEntity;
import com.alkemy.ong.entity.SlideEntity;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.mapper.SlideMapperImpl;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;
import com.alkemy.ong.util.OrganizationMock;
import com.alkemy.ong.util.SlideMock;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
public class SlideControllerTest {
    @InjectMocks
    public SlideController slideController;
    @MockBean
    public SlideService slideService;
    @Autowired
    public MockMvc mockMvc;
    @Spy
    private final SlideMapper slideMapper = new SlideMapperImpl() {
    };
    @Mock
    private SlideRepository slideRepositoryMock  = Mockito.mock(SlideRepository.class);
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }
    //Slide Entity
    @Spy
    private final SlideMock slideMock = new SlideMock();
    @Spy
    private final OrganizationMock ongMock = new OrganizationMock();

    //============================================================/
            /*                          GET-TEST                        */
            //============================================================/
    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void getSlides_ByAdmin() throws Exception {
        SlideBasicDto dto = slideMock.mockSlideBasicDto();
        ArrayList<SlideBasicDto> slidesList= new ArrayList<>();
        slidesList.add(dto);
        Mockito.when(slideService.getAllSlides()).thenReturn(slidesList);
        mockMvc.perform(MockMvcRequestBuilders.get("/slides"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "userMock", authorities = "NON_REGISTER")
    void getSlides_NonRegister_Forbidden() throws Exception {
        SlideBasicDto dto = slideMock.mockSlideBasicDto();
        ArrayList<SlideBasicDto> slidesList= new ArrayList<>();
        slidesList.add(dto);
        Mockito.when(slideService.getAllSlides()).thenReturn(slidesList);
        mockMvc.perform(MockMvcRequestBuilders.get("/slides"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void getSlidesById_statusOk() throws Exception {
        String id = "1";
        SlideDtoFull slideFullDto = slideMock.mockSlideFullDto();
        SlideEntity slideEntity = slideMapper.slideDtoFullToSlideEntity(slideFullDto);
        Mockito.when(slideRepositoryMock.save(slideEntity)).thenReturn(slideEntity);
        Mockito.when(slideService.getSlide(id)).thenReturn(slideFullDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/slides/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //============================================================/
            /*                         DELETE-TEST                        */
            //============================================================/
    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void testDeleteSlide_shouldReturnSuccessfully_ADMIN() throws Exception {
        SlideEntity slideEntity = slideMock.mockSlideEntity();
        Mockito.when(slideRepositoryMock.save(slideEntity)).thenReturn(slideEntity);
        SlideEntity slideEntitySoftDeleted = slideEntity;
        slideEntitySoftDeleted.setSoftDelete(true);
        Mockito.when(slideRepositoryMock.findById(slideEntity.getId())).thenReturn(java.util.Optional.of(slideEntity));
        Mockito.when(slideRepositoryMock.save(slideEntitySoftDeleted)).thenReturn(slideEntitySoftDeleted);

        String updatedContent = objectWriter.writeValueAsString(slideEntitySoftDeleted);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/slides/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void TestDeleteSlideController_ADMIN_Not_Found() throws Exception {
        SlideBasicDto dto = slideMock.mockSlideBasicDto();
        when(slideController.delete("123")).thenThrow(new NullPointerException());
        slideController.delete("123");
    }

    //============================================================/
            /*                           PUT-TEST                         */
            //============================================================/

    /* controller test to update fields of an object of type Slide Entity.
    returns an updated object */
    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void testPutSlideController_shouldReturnIsOk_ADMIN() throws Exception {
        OrganizationEntity ongEntity =  ongMock.mockOrganizationEntity();
        SlideEntity slideEntity = slideMock.mockSlideEntity();
        String id = "1";
        Mockito.when(slideRepositoryMock.save(slideEntity)).thenReturn(slideEntity);
        SlideDtoFull slideFullDtoUpdate = slideMock.mockSlideFullDto();
        slideFullDtoUpdate.setOrganizationId(ongEntity);
        SlideEntity slideEntityUpdate = slideMapper.slideDtoFullToSlideEntity(slideFullDtoUpdate);
        Mockito.when(slideRepositoryMock.findById(slideEntity.getId())).thenReturn(java.util.Optional.of(slideEntity));
        Mockito.when(slideRepositoryMock.save(slideEntityUpdate)).thenReturn(slideEntityUpdate);
        Mockito.when(slideService.update(id,slideFullDtoUpdate)).thenReturn(slideFullDtoUpdate);
        String updatedContent = objectWriter.writeValueAsString(slideEntityUpdate);
        RequestBuilder request = MockMvcRequestBuilders
                .put("/slides/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(request)
                .andExpect(status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }
    //============================================================/
            /*                           POST-TEST                         */
            //============================================================/
    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void createSlides_ByAdmin() throws Exception {
        SlideEntity slideEntity = slideMock.mockSlideEntity();
        SlideDtoFull dtoFull = slideMapper.slideEntityToSlideDtoFull(slideEntity);
        String content = objectWriter.writeValueAsString(dtoFull);
        Mockito.when(slideService.createSlide(dtoFull)).thenReturn(dtoFull);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/slides")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }
}