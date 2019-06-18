package com.kyriba.conference.discount.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Igor Lizura
 */
@ApiModel(value = "DiscountUpdateParams", description = "Discount update parameters")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountUpdateParamsDto {
    @ApiModelProperty(value = "Discount percentage")
    @Min(0)
    @Max(100)
    int percentage;
}
