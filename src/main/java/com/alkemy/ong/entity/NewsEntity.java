package com.alkemy.ong.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "news")
@Setter
@Getter
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE news SET softDeleted = true WHERE id=?")
@Where(clause = "softDeleted=false")
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
    @JoinColumn(name="categoryId")
    private CategoryEntity categoryId;

    @Column(name="softDelete")
    private Boolean softDelete;


}
