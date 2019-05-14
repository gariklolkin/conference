package com.kyriba.payment.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Garik Lizura
 */
@Data
@NoArgsConstructor
public class PaymentMethod {
    private PaymentMethodType type;
    private String description;
    private String url;
}
