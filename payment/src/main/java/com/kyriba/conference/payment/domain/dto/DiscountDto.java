package com.kyriba.conference.payment.domain.dto;

import com.kyriba.conference.payment.domain.DiscountType;
import lombok.Getter;


/**
 * @author Igor Lizura
 */
@Getter
public class DiscountDto {
    private int percentage;
    private DiscountType type;
}
