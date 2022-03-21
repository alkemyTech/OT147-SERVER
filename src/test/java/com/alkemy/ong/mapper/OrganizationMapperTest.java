package com.alkemy.ong.mapper;


import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.OrganizationPublicDto;
import com.alkemy.ong.entity.OrganizationEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrganizationMapperTest {
    private static OrganizationMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper= Mappers.getMapper(OrganizationMapper.class);

    }
    @Test
    void organizationDtoToOrganization() {
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName("New");
        organizationEntity.setImage("this is an image");
        organizationEntity.setAddress("This is an address");
        organizationEntity.setPhone(124354);

        OrganizationDto ongDto = mapper.organizationToOrganizationDto(organizationEntity);
        assertAll(
                () -> {
                    assertEquals(organizationEntity.getName(), ongDto.getName());
                    assertEquals(organizationEntity.getImage(),ongDto.getImage());
                    assertEquals(organizationEntity.getAddress(),ongDto.getAddress());
                    assertEquals(organizationEntity.getPhone(),ongDto.getPhone());
                }
        );
    }
    @Test
    public void organizationDtoMapperTestSimpleNull(){
        OrganizationEntity entity=mapper.organizationDtoToOrganization(null);
        assertEquals(null,entity);
    }
    @Test
    void organizationToOrganizationDto() {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setName("New");
        organizationDto.setImage("this is an image");
        organizationDto.setAddress("This is an address");
        organizationDto.setPhone(124354);

        OrganizationEntity ongEntity = mapper.organizationDtoToOrganization(organizationDto);
        assertAll(
                () -> {
                    assertEquals(organizationDto.getName(), ongEntity.getName());
                    assertEquals(organizationDto.getImage(),ongEntity.getImage());
                    assertEquals(organizationDto.getAddress(),ongEntity.getAddress());
                    assertEquals(organizationDto.getPhone(),ongEntity.getPhone());
                }
        );
    }
    @Test
    public void organizationMapperTestSimpleNull(){
        OrganizationDto dto=mapper.organizationToOrganizationDto(null);
        assertEquals(null,dto);
    }
    @Test
    void organizationPublicDtoToOrganization() {
        OrganizationEntity organizationEntity = OrganizationEntity.builder()
                .id("id_number")
                .name("Example")
                .image("this is an image")
                .address("This is an address")
                .phone(124354)
                .facebookUrl("Facebook/example")
                .linkedinUrl("linkedIn/example")
                .instagramUrl("Instagram/example")
                .build();

        OrganizationPublicDto ongDto = mapper.organizationToOrganizationPublicDto(organizationEntity);
        assertAll(
                () -> {
                    assertEquals(organizationEntity.getName(), ongDto.getName());
                    assertEquals(organizationEntity.getImage(),ongDto.getImage());
                    assertEquals(organizationEntity.getAddress(),ongDto.getAddress());
                    assertEquals(organizationEntity.getPhone(),ongDto.getPhone());
                    assertEquals(organizationEntity.getFacebookUrl(),ongDto.getFacebookUrl());
                    assertEquals(organizationEntity.getLinkedinUrl(),ongDto.getLinkedinUrl());
                    assertEquals(organizationEntity.getInstagramUrl(),ongDto.getInstagramUrl());
                }
        );
    }
    @Test
    public void organizationIPublicDtoMapperTestSimpleNull(){
        OrganizationEntity entity=mapper.organizationPublicDtoToOrganization(null);
        assertEquals(null,entity);
    }
    @Test
    void organizationToOrganizationPublicDto() {
        OrganizationPublicDto organizationPublicDto = OrganizationPublicDto.builder()
                .id("id_number")
                .name("Example")
                .image("this is an image")
                .address("This is an address")
                .phone(124354)
                .facebookUrl("Facebook/example")
                .linkedinUrl("linkedIn/example")
                .instagramUrl("Instagram/example")
                .build();

        OrganizationEntity ongEntity = mapper.organizationPublicDtoToOrganization(organizationPublicDto);
        assertAll(
                () -> {
                    assertEquals(organizationPublicDto.getName(), ongEntity.getName());
                    assertEquals(organizationPublicDto.getImage(),ongEntity.getImage());
                    assertEquals(organizationPublicDto.getAddress(),ongEntity.getAddress());
                    assertEquals(organizationPublicDto.getPhone(),ongEntity.getPhone());
                    assertEquals(organizationPublicDto.getFacebookUrl(),ongEntity.getFacebookUrl());
                    assertEquals(organizationPublicDto.getLinkedinUrl(),ongEntity.getLinkedinUrl());
                    assertEquals(organizationPublicDto.getInstagramUrl(),ongEntity.getInstagramUrl());
                }
        );
    }
    @Test
    public void organizationPublicMapperTestSimpleNull(){
        OrganizationPublicDto dto=mapper.organizationToOrganizationPublicDto(null);
        assertEquals(null,dto);
    }
}
