package com.alkemy.ong.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Table(name = "slides")
@Builder
@AllArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE news SET soft_delete = true WHERE id=?")
@NoArgsConstructor
@Where(clause = "soft_delete=false")
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
    private OrganizationEntity organizationId;
    @Column(name="soft_delete")
    private Boolean softDelete = Boolean.FALSE;
}
