package com.alkemy.ong.entity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name="organizations")
@SQLDelete(sql = "UPDATE organizations SET deleted = true WHERE id=?")
public class Organization {
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
    private int phone;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "WELCOME_TEXT", nullable = false)
    private String welcomeText;

    @Column(name = "ABOUT_US_TEXT")
    private String aboutUsText;

    private boolean deleted=Boolean.FALSE;

    @Column(name= "creationDate" ,nullable = false, updatable = false,
            columnDefinition="TIMESTAMP WITH DATE")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(image, that.image) && Objects.equals(email, that.email) && Objects.equals(welcomeText, that.welcomeText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, email, welcomeText);
    }
}
