package com.alkemy.ong.dto;

import com.alkemy.ong.entity.OrganizationEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlideDtoFull {

    private String id;
    @NotBlank(message = "An image encoded in base64 must be specified.")
    private String imageUrl;
    @NotBlank(message = "A text for the slide must be provided.")
    private String text;
    private Integer order;
    private OrganizationEntity organizationId;
}
