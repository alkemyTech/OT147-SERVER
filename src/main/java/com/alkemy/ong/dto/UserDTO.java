package com.alkemy.ong.dto;

import com.alkemy.ong.entity.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDTO {
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
