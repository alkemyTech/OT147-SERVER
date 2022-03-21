package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityDto;
import com.alkemy.ong.entity.ActivityEntity;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.mapper.ActivityMapperImpl;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;
import com.alkemy.ong.util.ActivityMock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
public class ActivityControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();

    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ActivityRepository activityRepository = Mockito.mock(ActivityRepository.class);
    @MockBean
    private ActivityService activityService;
    @Autowired
    private MockMvc mockMvc;
    @Spy
    private final ActivityMapper mapper = new ActivityMapperImpl();

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    //Activity Entity
    @Spy
    private final ActivityMock activityMock = new ActivityMock();

    /*============================================================*/
    /*                           POST-TEST                        */
    /*============================================================*/

    //Controller test to create an object of type ActivityEntity with ROlE_ADMIN
    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void testPost_CreateActivity_shouldReturnCreated_ADMIN() throws Exception {
        ActivityEntity activityEntity = activityMock.mockActivityEntity();
        ActivityDto dto = mapper.activityToActivityDto(activityEntity);
        String content = objectWriter.writeValueAsString(dto);
        Mockito.when(activityService.addActivity(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/activities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(request)
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }
    /*============================================================*/
    /*                           PUT-TEST                         */
    /*============================================================*/

    /* Controller test to update fields of an object of type Activity Entity with ROlE_ADMIN
    returns an updated object */
    @Test
    @WithMockUser(username = "userMock", authorities = "ADMIN")
    void testPut_updateActivity_shouldReturnIsOk_ADMIN() throws Exception {
       String id = "10";
       ActivityEntity activityEntity = activityMock.mockActivityEntity();
       Mockito.when(activityRepository.save(activityEntity)).thenReturn(activityEntity);
       ActivityDto activityDtoUpdated = mapper.activityToActivityDto(activityEntity);
       activityDtoUpdated.setName("new name");
       Mockito.when(activityRepository.findById(activityEntity.getId())).thenReturn(java.util.Optional.of(activityEntity));
       Mockito.when(activityService.update("10",activityDtoUpdated)).thenReturn(activityDtoUpdated);
       String updatedContent = objectWriter.writeValueAsString(activityDtoUpdated);
       MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/activities/10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }
}
