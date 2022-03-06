package com.alkemy.ong.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@SQLDelete(sql = "UPDATE contact SET deletedAt = true WHERE id = ?")
@Where(clause = "deletedAt = false ")
public class ContactEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name= "uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "NAME", nullable = false)
    @NotEmpty(message = "The field must not be empty.")
    private String name;

    @Column(name = "PHONE", nullable = false)
    @NotEmpty(message = "The field must not be empty.")
    private String phone;

    @Column(name = "EMAIL", nullable = false)
    @Email(message = "email format error")
    private String email;

    @Column(name ="MESSAGE", nullable = false)
    @NotEmpty(message = "The message must not be empty.")
    private String message;

    private boolean deleteAt = Boolean.FALSE;

}
