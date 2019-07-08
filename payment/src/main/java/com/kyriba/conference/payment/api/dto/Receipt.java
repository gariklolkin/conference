package com.kyriba.conference.payment.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyriba.conference.payment.domain.PaymentStatus;
import com.kyriba.conference.payment.domain.dto.Amount;
import io.swagger.annotations.ApiModel;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ApiModel(description = "Ticket")
@Value
public class Receipt {
    @Valid
    @NotNull
    private Amount price;

    @JsonFormat(pattern = "dd::MM::yyyy HH:mm")
    LocalDateTime paymentDate;

    @NotNull
    private PaymentStatus status;
}