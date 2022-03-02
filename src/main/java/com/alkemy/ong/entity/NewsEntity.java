package com.alkemy.ong.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "news")
@SQLDelete(sql = "UPDATE news SET soft_delete = true WHERE id=?")
@NoArgsConstructor
@Where(clause = "soft_delete=false")
public class NewsEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamps;

    @ManyToOne
    @JoinColumn(name="category_id")
    private CategoryEntity categoryId;

    @Column(name="softDelete")
    private boolean softDelete=Boolean.FALSE;
}
