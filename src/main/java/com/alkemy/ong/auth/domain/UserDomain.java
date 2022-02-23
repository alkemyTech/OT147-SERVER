package com.alkemy.ong.auth.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDomain {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String photo;
    private RoleDomain roleid;
    private LocalDateTime timestamps;
}
