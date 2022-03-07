package com.alkemy.ong.dto;

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
    private String id;
    @NotEmpty(message = "The field must not be empty.")
    private String name;
    @NotEmpty(message = "The field must not be empty.")
    private String phone;
    @Email(message = "email format error")
    private String email;
    @NotEmpty(message = "The message must not be empty.")
    private String message;
    private boolean deleteAt = Boolean.FALSE;
}
