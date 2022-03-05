package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {

    @NotBlank(message = "the name field cannot be empty")
    @NotNull(message = "field name cannot be null")
    private String name;
    @NotBlank(message = "the name field cannot be empty")
    @NotNull(message = "field name cannot be null")
    private String content;
    private String image;

}
