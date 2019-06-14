package com.kyriba.conference.payment.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyriba.conference.payment.api.dto.PaymentDto;
import com.kyriba.conference.payment.api.dto.PaymentUpdateParamsDto;
import com.kyriba.conference.payment.api.dto.TicketDto;
import com.kyriba.conference.payment.domain.PaymentStatus;
import com.kyriba.conference.payment.domain.dto.Amount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static com.kyriba.conference.payment.domain.PaymentMethodType.CREDIT_CARD;
import static com.kyriba.conference.payment.domain.PaymentStatus.PENDING;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


/**
 * @author Igor Lizura
 */
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Valid List<PaymentDto> getPayments() {
        return Collections.singletonList(
                new PaymentDto(123,
                        CREDIT_CARD,
                        LocalDateTime.of(2019, Month.MAY, 12, 20, 30),
                        new Amount(new BigDecimal(250), Currency.getInstance(Locale.FRANCE)),
                        PENDING));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Valid Receipt createPayment(@ApiParam(value = "Payment creation object", required = true)
                                     @Valid @RequestBody TicketDto ticket) {
        return new Receipt(
                new Amount(new BigDecimal(250), Currency.getInstance(Locale.FRANCE)),
                new Amount(new BigDecimal(50), Currency.getInstance(Locale.FRANCE)),
                LocalDateTime.of(2019, Month.MAY, 12, 20, 30),
                PENDING);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{paymentId}", produces = APPLICATION_JSON_UTF8_VALUE)
    @Valid PaymentDto getPayment(@ApiParam(value = "Payment id", required = true) @PathVariable long paymentId) {
        return new PaymentDto(123,
                CREDIT_CARD,
                LocalDateTime.of(2019, Month.MAY, 12, 20, 30),
                new Amount(new BigDecimal(250), Currency.getInstance(Locale.FRANCE)),
                PENDING);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{paymentId}")
    void updatePayment(@ApiParam(value = "Payment id", required = true) @PathVariable long paymentId,
                              @ApiParam(value = "Payment updating parameters", required = true) @Valid @RequestBody PaymentUpdateParamsDto params) {
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{paymentId}")
    void deletePayment(@ApiParam(value = "Payment id", required = true) @PathVariable long paymentId) {
    }

    @ApiModel(description = "Ticket")
    @Value
    private static class Receipt {
        @Valid
        @NotNull
        private Amount price;

        @Valid
        @NotNull
        private Amount discount;

        @JsonFormat(pattern = "dd::MM::yyyy HH:mm")
        LocalDateTime time;

        @NotNull
        private PaymentStatus status;
    }
}
