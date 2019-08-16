package com.kyriba.conference.discount.api.dto;

import com.kyriba.conference.discount.domain.DiscountType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Igor Lizura
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DiscountType", description = "Discount response")
public class DiscountResponse {
    @ApiModelProperty(value = "Discount type")
    @NotNull
    private DiscountType type;
}
