package com.kyriba.conference.payment.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyriba.conference.payment.domain.DiscountType;
import com.kyriba.conference.payment.domain.PaymentMethodType;
import com.kyriba.conference.payment.domain.dto.Amount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * @author Igor Lizura
 */
@ApiModel(value = "Ticket", description = "Ticket for payment creation")
@Getter
public class TicketDto {
    @ApiModelProperty(value = "User id")
    @NotNull
    @Positive
    private int userId;

    @ApiModelProperty(value = "Payment type")
    @NonNull
    PaymentMethodType type;

    @ApiModelProperty(value = "Discount type")
    @NonNull
    DiscountType discountType;

    @ApiModelProperty(value = "Payment date")
    @NonNull
    @JsonFormat(pattern = "dd::MM::yyyy HH:mm")
    private LocalDateTime paymentDate;

    @ApiModelProperty(value = "Payment price")
    @Valid
    @NonNull
    private Amount price;
}
