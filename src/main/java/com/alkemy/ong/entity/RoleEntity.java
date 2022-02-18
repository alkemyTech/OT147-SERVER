package com.alkemy.ong.entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "role")
@Getter
@Setter
@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(length = 200)
    private String description;
    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;
    @OneToMany
    private List<UserEntity> userEntities;
}
