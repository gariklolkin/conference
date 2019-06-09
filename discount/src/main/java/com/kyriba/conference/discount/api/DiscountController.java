package com.kyriba.conference.discount.api;

import com.kyriba.conference.discount.api.dto.DiscountDto;
import com.kyriba.conference.discount.api.dto.DiscountPercentageDto;
import com.kyriba.conference.discount.domain.DiscountType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

import static com.kyriba.conference.discount.domain.DiscountType.JUNIOR;
import static com.kyriba.conference.discount.domain.DiscountType.STUDENT;

/**
 * @author Igor Lizura
 */
@RestController
@RequestMapping(value = "/api/v1/discounts")
public class DiscountController {
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    List<DiscountDto> getDiscounts() {
        return Arrays.asList(new DiscountDto(JUNIOR, 30),
                new DiscountDto(STUDENT, 50));
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    @Valid DiscountResponse createDiscount(
            @ApiParam(value = "Discount creation object", required = true) @Valid @RequestBody DiscountDto discount) {
        return new DiscountResponse(STUDENT);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{type}")
    DiscountDto getDiscount(@ApiParam(value = "Discount type", required = true) @PathVariable String type) {
        return new DiscountDto(STUDENT, 30);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/{type}")
    void updateDiscount(@ApiParam(value = "Discount type", required = true) @PathVariable String type,
                        @ApiParam(value = "Discount update parameters", required = true)
                        @Valid @RequestBody DiscountPercentageDto params) {
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{type}")
    void deleteDiscount(@ApiParam(value = "Discount type", required = true) @PathVariable String type) {
    }

    @ApiModel(value = "DiscountType", description = "Discount response")
    @Value
    private class DiscountResponse {
        @ApiModelProperty(value = "Discount type")
        @NotNull
        DiscountType type;
    }
}
