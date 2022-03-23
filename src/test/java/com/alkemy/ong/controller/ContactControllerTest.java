package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactDto;
import com.alkemy.ong.entity.ContactEntity;
import com.alkemy.ong.mapper.ContactMapper;
import com.alkemy.ong.mapper.ContactMapperImpl;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.ContactService;
import com.alkemy.ong.util.TestEntitiesCreation;
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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.alkemy.ong.util.DocumentationResponse.ADMIN;
import static com.alkemy.ong.util.DocumentationResponse.USER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
public class ContactControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
    @MockBean
    private ContactService contactService;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private ContactController contactController;
    @Spy
    private final ContactMapper mapper = new ContactMapperImpl();

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    //ContactEntity
    @Spy
    private final TestEntitiesCreation testEntitiesCreation = new TestEntitiesCreation();

    /*============================================================*/
    /*                           POST-TEST                        */
    /*============================================================*/

    //controller test to create an object of type contactEntity with ROlE_USER
    @Test
    @WithUserDetails(USER)
    void testCreateContact_shouldReturnCreated_user() throws Exception {
        ContactEntity contactEntity = testEntitiesCreation.exampleContactEntity();
        ContactDto dto = mapper.contactEntityToContactDto(contactEntity);

        String content = objectWriter.writeValueAsString(dto);

        Mockito.when(contactService.saveContact(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());

    }

    /* controller test to update fields of an object of type ContactEntity.
     unauthorized role */
    @Test
    @WithUserDetails(ADMIN)
    void testCreateContact_shouldReturnIsForbidden_admin() throws Exception {
        ContactEntity contactEntity = testEntitiesCreation.exampleContactEntity();
        ContactDto dto = mapper.contactEntityToContactDto(contactEntity);

        String content = objectWriter.writeValueAsString(dto);

        Mockito.when(contactService.saveContact(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(status().isForbidden());

    }

    /* controller test to create an object of type ContactEntity
    returns a bad request because the name field is empty*/
    @Test
    @WithUserDetails(USER)
    void testCreateContact_shouldReturnIsBadRequestEmptyField_user() throws Exception {

        ContactEntity contactEntity = testEntitiesCreation.exampleContactEntity();
        contactEntity.setName("");
        ContactDto dto = mapper.contactEntityToContactDto(contactEntity);

        String content = objectWriter.writeValueAsString(dto);
        Mockito.when(contactService.saveContact(dto)).thenReturn(dto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    /* controller test to create an object of type ContactEntity
     returns a bad request because the email field is incorrect*/
    @Test
    @WithUserDetails(USER)
    void testCreateContact_shouldReturnIsBabRequest_user() throws Exception {

        ContactEntity contactEntity = testEntitiesCreation.exampleContactEntity();
        contactEntity.setEmail("emailIncorrect");
        ContactDto dto = mapper.contactEntityToContactDto(contactEntity);

        String content = objectWriter.writeValueAsString(dto);
        Mockito.when(contactService.saveContact(dto)).thenReturn(dto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

    }

    /*============================================================*/
    /*                          GET-TEST                          */
    /*============================================================*/

    @Test
    @WithUserDetails(ADMIN)
    void testGetListContacts_shouldReturnIsOk_admin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /* controller test to get a list of an object of type ContactEntity.
          unauthorized role */
    @Test
    @WithUserDetails(USER)
    void testGetListContacts_shouldReturnIsForbidden_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contacts"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
