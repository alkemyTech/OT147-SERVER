package com.alkemy.ong.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "contacts")
@Data
@SQLDelete(sql = "UPDATE contacts SET deleted_at = true WHERE id=?")
@NoArgsConstructor
@Where(clause = "deleted_at=false")

public class ContactEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name= "uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;
    @Column(name = "name", nullable = false)
    @NotEmpty(message = "The field must not be empty.")
    private String name;
    private String phone;
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "email format error")
    private String email;
    private String message;
    @Column(name ="deleted_at")
    private boolean deletedAt = Boolean.FALSE;
}
