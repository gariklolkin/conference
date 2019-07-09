package com.kyriba.conference.discount.api;

import com.kyriba.conference.discount.api.dto.DiscountDto;
import com.kyriba.conference.discount.api.dto.DiscountResponse;
import com.kyriba.conference.discount.api.dto.DiscountPercentageUpdateDto;
import com.kyriba.conference.discount.domain.DiscountType;
import com.kyriba.conference.discount.service.DiscountService;
import com.kyriba.conference.discount.service.NoSuchDiscountException;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Igor Lizura
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/discounts")
@AllArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @GetMapping
    List<DiscountDto> getDiscounts() {
        return discountService.getAllDiscounts();
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    @Valid DiscountResponse createDiscount(
            @ApiParam(value = "Discount creation object", required = true) @Valid @RequestBody DiscountDto discount) {
        return discountService.createDiscount(discount);
    }

    @GetMapping("/{type}")
    DiscountDto getDiscount(@ApiParam(value = "Discount type", required = true) @PathVariable DiscountType type) {
        return discountService.getDiscount(type);
    }

    @PutMapping("/{type}")
    void updateDiscount(@ApiParam(value = "Discount type", required = true) @PathVariable DiscountType type,
                        @ApiParam(value = "Discount update parameters", required = true)
                        @Valid @RequestBody DiscountPercentageUpdateDto params) {
        discountService.updateDiscount(type, params);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{type}")
    void deleteDiscount(@ApiParam(value = "Discount type", required = true) @PathVariable DiscountType type) {
        discountService.deleteDiscount(type);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(ConstraintViolationException ex)  {
        log.error(ex.getMessage(), ex);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchDiscountException.class)
    public void handleNotFoundDiscountException(NoSuchDiscountException ex) {
        log.error(ex.getMessage(), ex);
    }
}
