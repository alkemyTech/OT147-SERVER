package com.alkemy.ong.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberDto {
    private String id;
    @NotBlank(message = "the name field cannot be empty")
    @NotNull(message = "field name cannot be null")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "The name cannot contain numbers or characters other than letters")
    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String image;
    private String description;
    private LocalDateTime timestamps;
    private boolean softDelete;
}
