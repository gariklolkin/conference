package com.kyriba.conference.payment.api.dto;

import com.kyriba.conference.payment.domain.PaymentMethodType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Igor Lizura
 */
@ApiModel(value = "PaymentMethod", description = "Payment method creation object")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto {
    @ApiModelProperty(value = "Payment method type")
    @NonNull
    private PaymentMethodType type;

    @ApiModelProperty(value = "url")
    @NonNull
    private String url;
}
