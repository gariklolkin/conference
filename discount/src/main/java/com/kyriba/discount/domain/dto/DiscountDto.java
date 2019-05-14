package com.kyriba.discount.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.kyriba.discount.domain.DiscountType;
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
public class DiscountDto {
    @NonNull
    private DiscountType type;

    int percentage;
}
