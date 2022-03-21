package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationPublicDto;
import com.alkemy.ong.dto.OrganizationUpdateDto;
import com.alkemy.ong.dto.SlidePublicOrganizationDto;
import com.alkemy.ong.entity.OrganizationEntity;
import com.alkemy.ong.mapper.OrganizationMapper;
import com.alkemy.ong.mapper.OrganizationMapperImpl;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.SlideService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
public class OrganizationControllerTest {

    @InjectMocks
    public OrganizationController organizationController;
    @MockBean
    public OrganizationService organizationService;
    @MockBean
    public SlideService slideService;
    @Autowired
    public MockMvc mockMvc;
    @Spy
    private final OrganizationMapper ongMapper = new OrganizationMapperImpl();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")

    void updateOrganization_StatusOK() throws Exception {

            OrganizationEntity ongEntity = createOrganizationEntity();
            OrganizationDto dto = ongMapper.organizationToOrganizationDto(ongEntity);
            OrganizationPublicDto publicDto = ongMapper.organizationToOrganizationPublicDto(ongEntity);
            OrganizationUpdateDto updateDto = createOrganizationUpdateDto();
            String content = objectWriter.writeValueAsString(publicDto);
            Mockito.when(organizationService.update(updateDto)).thenReturn(publicDto);
            RequestBuilder request = MockMvcRequestBuilders
                    .put("/organization/public")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content);
            mockMvc.perform(request)
                    .andExpect(status().is(200))
                    .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithMockUser(username = "userMock", authorities = "USER")

    void getOrganization_statusOk() throws Exception {
        String id = "10";
        SlidePublicOrganizationDto dto = createSlidePublicOrganizationDto();
        ArrayList<SlidePublicOrganizationDto> slidesList= new ArrayList<>();
        String content = objectWriter.writeValueAsString(dto);
        Mockito.when(slideService.getSlidesForOrganizationByOrder(id)).thenReturn(slidesList);
        mockMvc.perform(MockMvcRequestBuilders.get("/organization/public/10"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    private OrganizationEntity createOrganizationEntity(){
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId("1");
        organizationEntity.setName("New");
        organizationEntity.setImage("this is an image");
        organizationEntity.setAddress("This is an address");
        organizationEntity.setPhone(124354);
        organizationEntity.setSoftDelete(false);
        return organizationEntity;
    }
    private OrganizationUpdateDto createOrganizationUpdateDto(){
        OrganizationUpdateDto dto = new OrganizationUpdateDto();
        dto.setId("1");
        dto.setName("New");
        dto.setImage("this is an image");
        dto.setAddress("This is an address");
        dto.setPhone(124354);
        dto.setFacebookUrl("Mock facebook url");
        dto.setLinkedinUrl("Mock linkedIn url");
        dto.setInstagramUrl("Mock instagram url");
        return dto;
    }
    private OrganizationPublicDto createOrganizationPublicDto(){
        OrganizationPublicDto dto = new OrganizationPublicDto();
        dto.setId("1");
        dto.setName("New");
        dto.setImage("this is an image");
        dto.setAddress("This is an address");
        dto.setPhone(124354);
        dto.setFacebookUrl("Mock facebook url");
        dto.setLinkedinUrl("Mock linkedIn url");
        dto.setInstagramUrl("Mock instagram url");
        return dto;
    }
    private SlidePublicOrganizationDto createSlidePublicOrganizationDto(){
        OrganizationPublicDto publicDto =createOrganizationPublicDto();
        SlidePublicOrganizationDto dto = new SlidePublicOrganizationDto();
        dto.setId("12");
        dto.setOrganizationId(publicDto);
        dto.setOrder(1);
        dto.setImageUrl("Mock image url");
        dto.setText("This is some mock text");
        return dto;
    }
}
