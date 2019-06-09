package com.kyriba.conference.discount.api.dto;

import com.kyriba.conference.discount.domain.DiscountType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Igor Lizura
 */
@ApiModel(value = "Discount", description = "Discount creation request")
@Getter
@AllArgsConstructor
public class DiscountDto {
    @ApiModelProperty(value = "Discount type")
    @NotNull
    private DiscountType type;

    @ApiModelProperty(value = "Discount percentage")
    @Min(0)
    @Max(100)
    int percentage;
}
