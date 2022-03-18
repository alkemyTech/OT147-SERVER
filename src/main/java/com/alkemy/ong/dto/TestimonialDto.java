package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialDto {

    @NotBlank(message = "the name field cannot be empty")
    @NotNull(message = "field name cannot be null")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "The name cannot contain numbers or characters other than letters")
    private String name;
    @NotBlank(message = "the content field cannot be empty")
    @NotNull(message = "field content cannot be null")
    private String content;
    private String image;
}
