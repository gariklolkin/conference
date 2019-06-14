package com.kyriba.conference.payment.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyriba.conference.payment.domain.PaymentMethodType;
import com.kyriba.conference.payment.domain.PaymentStatus;
import com.kyriba.conference.payment.domain.dto.Amount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author Igor Lizura
 */
@ApiModel(value = "Payment", description = "Payment creation object")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    @ApiModelProperty(value = "User id")
    private long userId;

    @ApiModelProperty(value = "Payment type")
    @NonNull
    private PaymentMethodType type;

    @ApiModelProperty(value = "Payment date")
    @NonNull
    @JsonFormat(pattern = "dd::MM::yyyy HH:mm")
    private LocalDateTime paymentDate;

    @ApiModelProperty(value = "Payment price")
    @Valid
    @NonNull
    private Amount price;

    @ApiModelProperty(value = "Payment status")
    @NonNull
    private PaymentStatus status;
}
