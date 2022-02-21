package com.alkemy.ong.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Member")
public class MemberEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @Column( name="name", length = 50, nullable = false)
    private String name;

    @Column( name="facebookUrl", length = 100)
    private String facebookUrl;

    @Column( name="instagramUrl", length = 100)
    private String instagramUrl;

    @Column( name="linkedinUrl", length = 100)
    private String linkedinUrl;

    @NotNull
    @Column( name="image", length = 200, nullable = false)
    private String image;

    @Column( name="description", length = 250)
    private String description;

    @Column(name= "timeStamps" ,nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime timeStamps;

    @NotNull
    @Column(name = "softDelete", nullable = false)
    private boolean softDelete = Boolean.FALSE;


}
