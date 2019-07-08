package com.kyriba.conference.payment.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

/**
 * @author Igor Lizura
 */
@ApiModel(value = "PaymentMethodUpdateParams", description = "Payment method update parameters")
@Getter
public class PaymentMethodUrlUpdateDto {
    @ApiModelProperty(value = "Payment method url")
    @NotEmpty
    String url;
}
