package com.alkemy.ong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
@SQLDelete(sql = "UPDATE users SET softDelete = true WHERE id=?")
@Where(clause = "softDelete = false")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    /*
     I add validations in model javax.validation.constraints
     */

    @Column(name = "FIRST_NAME", nullable = false)
    @Size(min = 2, max = 12, message = "The length of the name must be between 2 to 12 characters.")
    @NotEmpty(message = "The field must not be empty.")
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    @Size(min = 2, max = 12, message = "The length of the name must be between 2 to 12 characters.")
    @NotEmpty(message = "The field must not be empty.")
    private String lastName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    @Email(message = "Must be a properly formatted email address.")
    @NotEmpty(message = "The field must not be empty.")
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    @Size(min = 8,message="The password must be at least eight characters.")
    @NotEmpty(message = "The field must not be empty.")
    private String password;

    @Column(name = "PHOTO")
    private String photo;

    @CreationTimestamp
    @Column(name = "TIMESTAMPS", nullable = false, updatable = false)
    private LocalDateTime timestamps;

    @Column(name = "SOFTDELETE", nullable = false)
    private Boolean softDelete = false;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private RoleEntity roleId;

}