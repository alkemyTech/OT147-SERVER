package com.alkemy.ong.dto;

import java.time.LocalDateTime;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class CategoryDtoFull {

    private String id;
    @NotBlank(message = "the name field cannot be empty")
    @NotNull(message = "field name cannot be null")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "The name cannot contain numbers or characters other than letters")
    private String name;
    private String description;
    private String image;
    private LocalDateTime timestamps;
    private boolean softDelete;
}