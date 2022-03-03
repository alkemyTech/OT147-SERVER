package com.alkemy.ong.entity;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "categories")
@Entity
@Data
@NoArgsConstructor
@SQLDelete(sql="UPDATE categories SET soft_delete = true WHERE id=?")
@Where(clause = "soft_delete=false")
public class CategoryEntity {

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

    @Column(name= "timestamps" ,nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamps;

    @Column(name = "soft_delete", nullable = false)
    private boolean softDelete=Boolean.FALSE;
}
