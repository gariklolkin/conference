package com.kyriba.conference.discount.api;

import com.kyriba.conference.discount.api.dto.DiscountExternalDto;
import com.kyriba.conference.discount.api.dto.DiscountResponse;
import com.kyriba.conference.discount.api.dto.DiscountUpdateParamsDto;
import com.kyriba.conference.discount.service.DiscountService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Igor Lizura
 */
@RestController
@RequestMapping(value = "/api/v1/discounts")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    List<DiscountExternalDto> getDiscounts() {
        return discountService.getAllDiscounts();
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    @Valid DiscountResponse createDiscount(
            @ApiParam(value = "Discount creation object", required = true) @Valid @RequestBody DiscountExternalDto discount) {
        return discountService.createDiscount(discount);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{type}")
    DiscountExternalDto getDiscount(@ApiParam(value = "Discount type", required = true) @PathVariable String type) {
        return discountService.getDiscount(type);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/{type}")
    void updateDiscount(@ApiParam(value = "Discount type", required = true) @PathVariable String type,
                        @ApiParam(value = "Discount update parameters", required = true)
                        @Valid @RequestBody DiscountUpdateParamsDto params) {
        discountService.updateDiscount(type, params);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{type}")
    void deleteDiscount(@ApiParam(value = "Discount type", required = true) @PathVariable String type) {
        discountService.deleteDiscount(type);
    }
}
