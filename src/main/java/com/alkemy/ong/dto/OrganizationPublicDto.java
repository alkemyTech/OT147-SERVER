package com.alkemy.ong.dto;

import lombok.Data;

@Data
public class OrganizationPublicDto {
    private String id;
    private String name;
    private String image;
    private Integer phone;
    private String address;
    private String facebookUrl;
    private String linkedinUrl;
    private String instagramUrl;
}
