package com.alkemy.ong.entity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
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
    @Column(name = "timestamps", nullable = false, updatable = false)
    private LocalDateTime timestamps;
    @OneToMany
    private List<UserEntity> userEntities;
}
