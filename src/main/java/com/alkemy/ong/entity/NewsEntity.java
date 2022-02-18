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
import java.util.List;

@Entity
@Table(name = "news")
@Setter
@Getter
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE news SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
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

    @Column(name= "creationDate" ,nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "associated_news",
            joinColumns = @JoinColumn(name= "news.id"),
            inverseJoinColumns = @JoinColumn(name = "category.id"))
    private List<CategoryEntity> categoryId;
    private Boolean deleted;


}
