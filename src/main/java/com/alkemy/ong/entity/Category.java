package com.alkemy.ong.entity;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "categorys")
@Entity
@Getter @Setter @ToString @RequiredArgsConstructor
@SQLDelete(sql="UPDATE categorys SET softDelete = true WHERE id=?")
@Where(clause = "softDelete=false")
public class Category {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column( nullable = false, length = 30)
    private String name;
    @Column( name="description")
    private String description;
    @Column( name="image")
    private String image;

    @Column(name= "creationDate" ,nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamps;

    @Column(name = "softDelete", nullable = false)
    private boolean softDelete=Boolean.FALSE;

}
