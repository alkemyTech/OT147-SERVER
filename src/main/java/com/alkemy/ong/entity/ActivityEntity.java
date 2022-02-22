package com.alkemy.ong.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="activities")
@Getter
@Setter
@SQLDelete(sql = "UPDATE activities SET softDelete = true WHERE id = ?")
@Where(clause = "softDelete = false ")
public class ActivityEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name= "uuid", strategy = "uuid2")
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    @Column(nullable = false, length = 50)
    private String name;
    @NotNull
    @Column(nullable = false, length = 150)
    private String content;
    @Column(nullable = false)
    private String image;

    private boolean softDelete = Boolean.FALSE;

    @Column(name= "timestamps" ,nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamps;



}

