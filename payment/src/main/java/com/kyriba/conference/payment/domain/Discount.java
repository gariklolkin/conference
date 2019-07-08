package com.kyriba.conference.payment.domain;

import lombok.Getter;

@Getter
public class Discount {
    int percentage;
    DiscountType type;
}
