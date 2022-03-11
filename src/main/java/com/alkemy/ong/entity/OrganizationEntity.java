package com.alkemy.ong.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@Table(name="organizations")
@SQLDelete(sql = "UPDATE organizations SET deleted = true WHERE id=?")
@NoArgsConstructor
@Where(clause = "soft_Delete = false")
public class OrganizationEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "IMAGE", nullable = false)
    private String image;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private Integer phone;

    @Column(name = "EMAIL", nullable = false)
    private String email;


    @Column(name = "WELCOME_TEXT", nullable = false, columnDefinition = "TEXT")
    private String welcomeText;


    @Column(name = "ABOUT_US_TEXT", columnDefinition = "TEXT")
    private String aboutUsText;

    private boolean softDelete=Boolean.FALSE;

    @Column(name= "TIMESTAMPS" ,nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime timeStamps;

    @Column(name = "FACEBOOK_URL")
    private String facebookUrl;

    @Column(name = "LINKEDIN_URL")
    private String linkedinUrl;

    @Column(name = "INSTAGRAM_URL")
    private String instagramUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationEntity that = (OrganizationEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(image, that.image) && Objects.equals(email, that.email) && Objects.equals(welcomeText, that.welcomeText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, email, welcomeText);
    }
}
