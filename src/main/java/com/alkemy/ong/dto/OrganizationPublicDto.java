package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrganizationPublicDto {
    @JsonIgnore
    private String id;
    private String name;
    private String image;
    private Integer phone;
    private String address;
    private String facebookUrl;
    private String linkedinUrl;
    private String instagramUrl;
}
