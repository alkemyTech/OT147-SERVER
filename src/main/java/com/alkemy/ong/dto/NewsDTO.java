package com.alkemy.ong.dto;

import com.alkemy.ong.entity.CategoryEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class NewsDTO {
    private String id;
    private String name;
    private String content;
    private String image;
    private LocalDateTime timestamps;
    private CategoryEntity categoryId;
    private Boolean softDelete;
}
