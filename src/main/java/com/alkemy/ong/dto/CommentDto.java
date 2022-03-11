package com.alkemy.ong.dto;

import com.alkemy.ong.entity.NewsEntity;
import com.alkemy.ong.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CommentDto {
    private String id;

    private String body;

    private NewsEntity newsId;

    private UserEntity userId;

    private LocalDateTime timestamps;

    private Boolean softDelete = false;
}
