package com.kyriba.conference.discount.service;

import com.kyriba.conference.discount.api.dto.DiscountExternalDto;
import com.kyriba.conference.discount.api.dto.DiscountResponse;
import com.kyriba.conference.discount.api.dto.DiscountUpdateParamsDto;

import java.util.List;

/**
 * @author Igor Lizura
 */
public interface DiscountService {
    List<DiscountExternalDto> getAllDiscounts();

    DiscountResponse createDiscount(DiscountExternalDto discountDto);

    DiscountExternalDto getDiscount(String type);

    void updateDiscount(String type, DiscountUpdateParamsDto params);

    void deleteDiscount(String type);
}