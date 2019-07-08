package com.kyriba.conference.payment.api.dto;

import com.kyriba.conference.payment.domain.PaymentStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * @author Igor Lizura
 */
@ApiModel(value = "PaymentUpdateParameters", description = "Payment update parameters")
@Getter
public class PaymentStatusUpdateDto {
    @ApiModelProperty(value = "Payment status")
    @NotNull
    private PaymentStatus status;
}
