package com.kyriba.conference.payment.domain;

import com.kyriba.conference.payment.api.dto.PaymentDto;
import com.kyriba.conference.payment.api.dto.Receipt;
import com.kyriba.conference.payment.api.dto.TicketDto;
import com.kyriba.conference.payment.domain.dto.Amount;
import com.kyriba.conference.payment.domain.dto.DiscountDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Currency;

/**
 * @author Igor Lizura
 */
@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class PaymentEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_identity_id")
    @SequenceGenerator(name = "seq_identity_id", sequenceName = "seq_identity_id", allocationSize = 1)
    @Id
    private Long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PaymentMethodType type;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    public PaymentEntity(DiscountDto discount, TicketDto ticketDto) {
        this.userId = ticketDto.getUserId();
        this.type = ticketDto.getType();
        this.paymentDate = ticketDto.getPaymentDate();
        this.value = ticketDto.getPrice().getValue();
        this.currency = ticketDto.getPrice().getCurrency().getCurrencyCode();
        applyDiscount(discount);

    }

    public Receipt toReceipt() {
        return new Receipt(new Amount(value, Currency.getInstance(currency)), paymentDate, status);
    }

    public PaymentDto toPaymentDto() {
        return new PaymentDto(userId, type, paymentDate, new Amount(value, Currency.getInstance(currency)), status);
    }

    private void applyDiscount(DiscountDto discount) {
        if (discount != null) {
            this.value = value.subtract(value.multiply(BigDecimal.valueOf(discount.getPercentage()))
                .divide(new BigDecimal(100), RoundingMode.UP));
        }
    }
}
