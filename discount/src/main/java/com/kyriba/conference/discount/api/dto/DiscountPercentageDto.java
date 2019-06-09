package com.kyriba.conference.discount.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Igor Lizura
 */
@ApiModel(value = "DiscountPercentage", description = "Discount updation request")
@Getter
public class DiscountPercentageDto {
    @ApiModelProperty(value = "Discount percentage")
    @Min(0)
    @Max(100)
    int percentage;
}
