package com.alkemy.ong.dto;

import com.alkemy.ong.entity.CategoryEntity;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
public class NewsDto {
    private String id;
    @NotNull(message = "field name cannot be null")
    @NotBlank(message = "the name field cannot be empty")
    private String name;
    @NotNull(message = "field name cannot be null")
    @NotBlank(message = "the name field cannot be empty")
    private String content;
    @NotNull(message = "field name cannot be null")
    @NotBlank(message = "the name field cannot be empty")
    private String image;
    private LocalDateTime timestamps;
    @NotNull(message = "field name cannot be null")
    private CategoryEntity categoryId;
    private Boolean softDelete;
}
