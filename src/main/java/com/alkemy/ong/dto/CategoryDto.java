package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class CategoryDto {

    @NotBlank(message = "the name field cannot be empty")
    @NotNull(message = "field name cannot be null")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "The name cannot contain numbers or characters other than letters")
    private String name;
    private String description;
    private String image;


}
