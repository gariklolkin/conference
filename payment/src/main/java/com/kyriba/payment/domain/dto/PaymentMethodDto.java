package com.kyriba.payment.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.kyriba.payment.domain.PaymentMethodType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Igor Lizura
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PaymentMethodDto {
    @NonNull
    private PaymentMethodType type;

    @NonNull
    private String url;
}
