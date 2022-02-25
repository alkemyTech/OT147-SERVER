package com.alkemy.ong.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "members")
@SQLDelete(sql = "UPDATE members SET softDelete = true WHERE id=?")
@Where(clause = "softDelete = false")
public class MemberEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @Column(  length = 50, nullable = false)
    private String name;

    @Column( name="facebook_url", length = 100)
    private String facebookUrl;

    @Column( name="instagram_url", length = 100)
    private String instagramUrl;

    @Column( name="linkedin_url", length = 100)
    private String linkedinUrl;

    @NotNull
    @Column( length = 200, nullable = false)
    private String image;

    @Column( length = 250)
    private String description;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamps;

    @NotNull
    @Column(name = "softDelete", nullable = false)
    private boolean softDelete = Boolean.FALSE;


}
