package com.alkemy.ong.dto;

import com.alkemy.ong.entity.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    @JsonIgnore
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private String photo;
    private RoleEntity roleId;
    private LocalDateTime timestamps;

}
