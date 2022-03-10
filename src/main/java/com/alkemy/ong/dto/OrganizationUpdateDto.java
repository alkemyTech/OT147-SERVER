package com.alkemy.ong.dto;

import lombok.Data;

@Data
public class OrganizationUpdateDto {
    private String id;
    private String name;
    private String image;
    private Integer phone;
    private String address;
    private String facebookUrl;
    private String linkedinUrl;
    private String instagramUrl;
}
