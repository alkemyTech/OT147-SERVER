package com.alkemy.ong.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "testimonials")
@Getter
@Setter
@SQLDelete(sql = "UPDATE testimonials SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class TestimonyEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @Column(name= "creationDate" ,nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

}
