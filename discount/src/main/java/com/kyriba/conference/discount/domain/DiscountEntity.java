package com.kyriba.conference.discount.domain;

import lombok.*;

import javax.persistence.*;

import static com.kyriba.conference.discount.domain.DiscountType.WITHOUT_DISCOUNT;

/**
 * @author Igor Lizura
 */
@Entity
@Table(name = "discount")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_identity_id")
    @SequenceGenerator(name = "seq_identity_id", sequenceName = "seq_identity_id", allocationSize = 1)
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DiscountType type = WITHOUT_DISCOUNT;

    @Column(name = "percentage")
    private int percentage;
}
