package com.alkemy.ong.dto;

import lombok.Data;

@Data
public class SlidePublicOrganizationDto {
    private String id;
    private String imageUrl;
    private String text;
    private Integer order;
    private OrganizationPublicDto organizationId;
}
