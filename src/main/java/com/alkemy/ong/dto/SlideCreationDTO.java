package com.alkemy.ong.dto;

import com.alkemy.ong.entity.OrganizationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class SlideCreationDTO implements Serializable {

    @NotBlank(message = "An image encoded in base64 must be specified.")
    private String imageUrl;
    @NotBlank(message = "A text for the slide must be provided.")
    private String text;
    private Integer order;
    private OrganizationEntity organizationId;

}
