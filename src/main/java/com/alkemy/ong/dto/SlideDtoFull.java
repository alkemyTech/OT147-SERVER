package com.alkemy.ong.dto;

import com.alkemy.ong.entity.OrganizationEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlideDtoFull {

    @JsonIgnore
    private String id;
    private String imageUrl;
    private String text;
    private Integer order;
    @JsonIgnore
    private OrganizationEntity organizationId;

}
