package com.alkemy.ong.controller;

import com.alkemy.ong.service.AmazonClient;
import com.alkemy.ong.util.ActivityMock;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.alkemy.ong.util.DocumentationResponse.USER;
import static org.hamcrest.Matchers.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static com.alkemy.ong.util.DocumentationResponse.ADMIN;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;


@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
public class BucketControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }
    @Spy
    private final ActivityMock activityMock = new ActivityMock();
    @InjectMocks
    private BucketController bucketController;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Test
    @WithUserDetails(ADMIN)
    void uploadFile() throws  Exception{
        AmazonClient amazonClient= Mockito.mock(AmazonClient.class);
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        ResultActions $ = mockMvc.perform(multipart("/storage/uploadFile").file(file))
                .andExpect(jsonPath("$", notNullValue()));
    }
    @Test
    @WithUserDetails(USER)
    void uploadFile_forbidden() throws  Exception{
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/news", file))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
