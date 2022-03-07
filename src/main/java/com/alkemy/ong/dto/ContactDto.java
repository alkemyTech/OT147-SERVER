package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContactDto  {
    @JsonIgnore
    private String id;
    @NotEmpty(message = "The field must not be empty.")
    private String name;
    private String phone;
    @Email(message = "email format error")
    private String email;
    private String message;
    @JsonIgnore
    private boolean deleteAt = Boolean.FALSE;
}
