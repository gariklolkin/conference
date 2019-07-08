package com.kyriba.conference.payment.api.dto;

import com.kyriba.conference.payment.domain.PaymentMethodType;
import lombok.Value;

@Value
public class PaymentMethodResponse {
    private PaymentMethodType type;
}
