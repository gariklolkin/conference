package com.kyriba.conference.payment.domain;

import com.kyriba.conference.payment.api.dto.PaymentMethodDto;
import com.kyriba.conference.payment.api.dto.PaymentMethodResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "payment_method")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_identity_id")
    @SequenceGenerator(name = "seq_identity_id", sequenceName = "seq_identity_id", allocationSize = 1)
    @Id
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PaymentMethodType type;

    @Column(name = "url")
    String url;

    public PaymentMethodEntity(PaymentMethodDto paymentMethodDto) {
        this.type = paymentMethodDto.getType();
        this.url = paymentMethodDto.getUrl();
    }

    public PaymentMethodDto toPaymentMethodDto() {
        return new PaymentMethodDto(type, url);
    }

    public PaymentMethodResponse toPaymentMethodResponse() {
        return new PaymentMethodResponse(type);
    }
}