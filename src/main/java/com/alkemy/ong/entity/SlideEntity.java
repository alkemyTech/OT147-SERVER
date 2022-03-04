package com.alkemy.ong.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Table(name = "slides")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    private OrganizationEntity organizationId;
}
