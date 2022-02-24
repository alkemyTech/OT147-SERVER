package com.alkemy.ong.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String password;
    private String photo;
    private LocalDateTime timestamps;
    private Boolean softDelete = false;
}
