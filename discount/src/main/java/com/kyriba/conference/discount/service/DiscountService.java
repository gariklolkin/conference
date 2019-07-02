package com.kyriba.conference.discount.service;

import com.kyriba.conference.discount.api.dto.DiscountDto;
import com.kyriba.conference.discount.api.dto.DiscountResponse;
import com.kyriba.conference.discount.api.dto.DiscountPercentageUpdateDto;
import com.kyriba.conference.discount.domain.DiscountType;

import java.util.List;

/**
 * @author Igor Lizura
 */
public interface DiscountService {
    List<DiscountDto> getAllDiscounts();

    DiscountResponse createDiscount(DiscountDto discountDto);

    DiscountDto getDiscount(DiscountType type);

    void updateDiscount(DiscountType type, DiscountPercentageUpdateDto params);

    void deleteDiscount(DiscountType type);
}