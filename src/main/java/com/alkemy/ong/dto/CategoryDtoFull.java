package com.alkemy.ong.dto;


import java.time.LocalDateTime;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CategoryDtoFull {

    private String id;

    private String name;
    private String description;

    private String image;
    private LocalDateTime timestamps;

    private boolean softDelete;
}
