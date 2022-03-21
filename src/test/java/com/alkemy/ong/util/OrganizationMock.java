package com.alkemy.ong.util;

import com.alkemy.ong.dto.OrganizationPublicDto;
import com.alkemy.ong.dto.OrganizationUpdateDto;
import com.alkemy.ong.dto.SlidePublicOrganizationDto;
import com.alkemy.ong.entity.OrganizationEntity;

public class OrganizationMock {
    public OrganizationUpdateDto mockOrganizationUpdateDto(){
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
    public OrganizationPublicDto mockOrganizationPublicDto(){
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
    public SlidePublicOrganizationDto mockSlidePublicOrganizationDto(){
        OrganizationPublicDto publicDto =mockOrganizationPublicDto();
        SlidePublicOrganizationDto dto = new SlidePublicOrganizationDto();
        dto.setId("12");
        dto.setOrganizationId(publicDto);
        dto.setOrder(1);
        dto.setImageUrl("Mock image url");
        dto.setText("This is some mock text");
        return dto;
    }
    public OrganizationEntity mockOrganizationEntity(){
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId("1");
        organizationEntity.setName("New");
        organizationEntity.setImage("this is an image");
        organizationEntity.setAddress("This is an address");
        organizationEntity.setPhone(124354);
        organizationEntity.setSoftDelete(false);
        return organizationEntity;
    }
}
