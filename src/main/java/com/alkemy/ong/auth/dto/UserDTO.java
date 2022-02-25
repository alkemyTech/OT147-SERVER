package com.alkemy.ong.auth.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private String photo;
    private LocalDateTime timestamps;
}
