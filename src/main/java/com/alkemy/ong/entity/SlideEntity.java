package com.alkemy.ong.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Table(name = "slides")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE slides SET softDeleted = true WHERE id=?") //This query provides the soft delete, as an update over slides
@Where(clause = "softDeleted=false")
@Entity
public class SlideEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String imageUrl;
    private String text;
    @Column (name = "order_number")
    private Integer order;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "organization")
    private OrganizationEntity organizationEntity;
}
