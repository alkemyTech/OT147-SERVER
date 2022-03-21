package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.entity.MemberEntity;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.mapper.MemberMapperImpl;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.MemberService;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc
@SpringBootTest
public class MemberControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private MemberRepository memberRepositoryMock = Mockito.mock(MemberRepository.class);
    @MockBean
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;
    @Spy
    private final MemberMapper mapper = new MemberMapperImpl();

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    //memberEntity
    @Spy
    private final TestEntitiesCreation testEntitiesCreation = new TestEntitiesCreation();

    /*============================================================*/
    /*                           POST-TEST                        */
    /*============================================================*/

    //controller test to create an object of type memberEntity with ROlE_USER
    @Test
    @WithUserDetails(USER)
    void testCreateMember_shouldReturnCreated_user() throws Exception {

        MemberEntity memberEntity = testEntitiesCreation.exampleMemberEntity();
        MemberDto dto = mapper.memberEntityToMemberDto(memberEntity);

        String content = objectWriter.writeValueAsString(dto);

        Mockito.when(memberService.addMember(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

    }

    //controller test to create an object of type memberEntity with ROlE_ADMIN
    @Test
    @WithUserDetails(ADMIN)
    void testCreateMember_shouldReturnIsForbidden_admin() throws Exception {

        MemberEntity memberEntity = testEntitiesCreation.exampleMemberEntity();
        MemberDto dto = mapper.memberEntityToMemberDto(memberEntity);

        String content = objectWriter.writeValueAsString(dto);

        Mockito.when(memberService.addMember(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    // controller test to create an object of type memberEntity with an unknown role
    @Test
    @WithMockUser(roles = "VISITOR")
    void testCreateMember_shouldReturnIsForbidden_client() throws Exception {

        MemberEntity memberEntity = testEntitiesCreation.exampleMemberEntity();
        MemberDto dto = mapper.memberEntityToMemberDto(memberEntity);

        String content = objectWriter.writeValueAsString(dto);

        Mockito.when(memberService.addMember(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    /* controller test to create an object of type memberEntity
    returns a bad request because the name field is empty*/
    @Test
    @WithUserDetails(USER)
    void testCreateMember_shouldReturnIsBadRequest_user() throws Exception {

        MemberEntity memberEntity = testEntitiesCreation.exampleMemberEntity();
        memberEntity.setName(" ");
        MemberDto dto = mapper.memberEntityToMemberDto(memberEntity);

        String content = objectWriter.writeValueAsString(dto);

        Mockito.when(memberService.addMember(dto)).thenReturn(dto);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());

    }

    /*============================================================*/
    /*                           PUT-TEST                         */
    /*============================================================*/

    /* controller test to update fields of an object of type memberEntity.
    returns an updated object */
    @Test
    @WithUserDetails(USER)
    void testPutTestimonialController_shouldReturnIsOk_user() throws Exception {

        MemberEntity memberEntity = testEntitiesCreation.exampleMemberEntity();
        Mockito.when(memberRepositoryMock.save(memberEntity)).thenReturn(memberEntity);

        MemberEntity memberEntityUpdate = new MemberEntity();
        memberEntityUpdate.setId("1f35fe0e");
        memberEntityUpdate.setName("new name");

        Mockito.when(memberRepositoryMock.findById(memberEntity.getId())).thenReturn(java.util.Optional.of(memberEntity));
        Mockito.when(memberRepositoryMock.save(memberEntityUpdate)).thenReturn(memberEntityUpdate);

        String updatedContent = objectWriter.writeValueAsString(memberEntityUpdate);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/members/1f35fe0e")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    /* controller test to update fields of an object of type memberEntity.
     unauthorized role  */
    @Test
    @WithUserDetails(ADMIN)
    void testPutTestimonialController_shouldReturnIsForbidden_admin() throws Exception {
        MemberEntity memberEntity = testEntitiesCreation.exampleMemberEntity();
        Mockito.when(memberRepositoryMock.save(memberEntity)).thenReturn(memberEntity);

        MemberEntity memberEntityUpdate = new MemberEntity();
        memberEntityUpdate.setId("1f35fe0e");
        memberEntityUpdate.setName("new name");

        Mockito.when(memberRepositoryMock.findById(memberEntity.getId())).thenReturn(java.util.Optional.of(memberEntity));
        Mockito.when(memberRepositoryMock.save(memberEntityUpdate)).thenReturn(memberEntityUpdate);

        String updatedContent = objectWriter.writeValueAsString(memberEntityUpdate);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/members/1f35fe0e")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isForbidden());
    }

    /* controller test to update fields of an object of type memberEntity.
     Id-unknown  */
    @Test
    @WithUserDetails(USER)
    void testPutTestimonialController_shouldReturnIsBadRequest_user() throws Exception {

        MemberEntity memberEntity = testEntitiesCreation.exampleMemberEntity();
        Mockito.when(memberRepositoryMock.save(memberEntity)).thenReturn(memberEntity);

        MemberEntity memberEntityUpdate = new MemberEntity();
        memberEntityUpdate.setDescription("New Description");

        Mockito.when(memberRepositoryMock.findById(memberEntity.getId())).thenReturn(java.util.Optional.of(memberEntity));
        Mockito.when(memberRepositoryMock.save(memberEntityUpdate)).thenReturn(memberEntityUpdate);

        String updatedContent = objectWriter.writeValueAsString(memberEntityUpdate);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/members/Id-unknown")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    /*============================================================*/
    /*                         DELETE-TEST                        */
    /*============================================================*/
    @Test
    @WithUserDetails(ADMIN)
    void testDeleteMember_shouldReturnSuccessfully_admin() throws Exception {

        MemberEntity memberEntity = testEntitiesCreation.exampleMemberEntity();
        Mockito.when(memberRepositoryMock.save(memberEntity)).thenReturn(memberEntity);

        MemberEntity memberEntitySoftDeleted = memberEntity;
        memberEntitySoftDeleted.setId("1f35fe0e");
        memberEntitySoftDeleted.setName("Name");
        memberEntitySoftDeleted.setFacebookUrl("facebookUrl");
        memberEntitySoftDeleted.setInstagramUrl("instagramUrl");
        memberEntitySoftDeleted.setLinkedinUrl("linkedinUrl");
        memberEntitySoftDeleted.setImage("../imageExample");
        memberEntitySoftDeleted.setDescription("description");
        memberEntitySoftDeleted.setSoftDelete(true);

        Mockito.when(memberRepositoryMock.findById(memberEntity.getId())).thenReturn(java.util.Optional.of(memberEntity));
        Mockito.when(memberRepositoryMock.save(memberEntitySoftDeleted)).thenReturn(memberEntitySoftDeleted);

        String updatedContent = objectWriter.writeValueAsString(memberEntitySoftDeleted);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/members/1f35fe0e")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

    }

    /* controller test to delete an object of type memberEntity.
     unauthorized role  */
    @Test
    @WithUserDetails(USER)
    void testDeleteMember_shouldReturnIsForbidden_user() throws Exception {

        MemberEntity memberEntity = testEntitiesCreation.exampleMemberEntity();
        Mockito.when(memberRepositoryMock.save(memberEntity)).thenReturn(memberEntity);

        MemberEntity memberEntitySoftDeleted = memberEntity;
        memberEntitySoftDeleted.setId("1f35fe0e");
        memberEntitySoftDeleted.setName("Name");
        memberEntitySoftDeleted.setFacebookUrl("facebookUrl");
        memberEntitySoftDeleted.setInstagramUrl("instagramUrl");
        memberEntitySoftDeleted.setLinkedinUrl("linkedinUrl");
        memberEntitySoftDeleted.setImage("../imageExample");
        memberEntitySoftDeleted.setDescription("description");
        memberEntitySoftDeleted.setSoftDelete(true);

        Mockito.when(memberRepositoryMock.findById(memberEntity.getId())).thenReturn(java.util.Optional.of(memberEntity));
        Mockito.when(memberRepositoryMock.save(memberEntitySoftDeleted)).thenReturn(memberEntitySoftDeleted);

        String updatedContent = objectWriter.writeValueAsString(memberEntitySoftDeleted);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/members/1f35fe0e")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isForbidden());

    }

    /*============================================================*/
    /*                          GET-TEST                        */
    /*============================================================*/

    @Test
    @WithUserDetails(ADMIN)
    void testGetListMember_shouldReturnIsOk_admin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/members".concat("?page=")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails(ADMIN)
    void testGetListMemberOtherPage_shouldReturnIsOk_admin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/members".concat("?page=1")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails(USER)
    void testGetListMember_shouldReturnIsOk_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/members".concat("?page=")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithUserDetails(USER)
    void testGetListMemberOtherPage_shouldReturnIsOk_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/members".concat("?page=1")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
