package com.alkemy.ong.dto;

import com.alkemy.ong.entity.CategoryEntity;
import lombok.Data;


import java.time.LocalDateTime;
@Data
public class NewsDto {
    private String id;
    private String name;
    private String content;
    private String image;
    private LocalDateTime timestamps;
    private CategoryEntity categoryId;
    private Boolean softDelete;
}
