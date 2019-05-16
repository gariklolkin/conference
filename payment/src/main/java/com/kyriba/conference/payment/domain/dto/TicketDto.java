package com.kyriba.conference.payment.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyriba.conference.payment.domain.DiscountType;
import com.kyriba.conference.payment.domain.PaymentMethodType;
import com.kyriba.conference.payment.domain.vo.Amount;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Igor Lizura
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TicketDto {

    private int userId;

    @NonNull
    PaymentMethodType type;

    @NonNull
    DiscountType discountType;

    @NonNull
    @JsonFormat(pattern = "dd::MM::yyyy HH:mm")
    private LocalDateTime paymentDate;

    @NonNull
    private Amount price;
}
