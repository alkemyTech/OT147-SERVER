package com.alkemy.ong.auth.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder
public class RoleDomain {
    private String id;
    private String name;
    private String description;
    private LocalDateTime timestamps;
}
