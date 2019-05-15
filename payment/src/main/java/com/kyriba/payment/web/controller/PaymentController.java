package com.kyriba.payment.web.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kyriba.payment.domain.PaymentMethodType;
import com.kyriba.payment.domain.PaymentStatus;
import com.kyriba.payment.domain.dto.PaymentDto;
import com.kyriba.payment.domain.vo.Amount;
import com.kyriba.payment.domain.dto.TicketDto;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import static com.kyriba.payment.domain.PaymentStatus.PENDING;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


/**
 * @author Igor Lizura
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v1/payment")
public class PaymentController {
    @ResponseBody
    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PaymentDto>> getPayments() {
        List<PaymentDto> response = Collections.singletonList(PaymentDto.builder()
                .userId(123)
                .type(PaymentMethodType.CREDIT_CARD)
                .paymentDate(LocalDateTime.of(2019, Month.MAY, 12, 20, 30))
                .price(new Amount(new BigDecimal(250), Currency.getInstance(Locale.FRANCE)))
                .status(PENDING)
                .build());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Receipt> createPayment(@RequestBody TicketDto ticket) {
        Receipt response = new Receipt(new Amount(new BigDecimal(250), Currency.getInstance(Locale.FRANCE)),
                new Amount(new BigDecimal(50), Currency.getInstance(Locale.FRANCE)),
                LocalDateTime.of(2019, Month.MAY, 12, 20, 30), PENDING);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping(value = "/{paymentId}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PaymentDto> getPayment(@PathVariable long paymentId) {
        PaymentDto response = PaymentDto.builder()
                .userId(123)
                .type(PaymentMethodType.CREDIT_CARD)
                .paymentDate(LocalDateTime.of(2019, Month.MAY, 12, 20, 30))
                .price(new Amount(new BigDecimal(250), Currency.getInstance(Locale.FRANCE)))
                .status(PENDING)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping(value = "/{paymentId}", produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable long paymentId, @RequestBody PaymentDto payment) {
        return new ResponseEntity<>(new PaymentResponse(3), HttpStatus.OK);
    }

    @PatchMapping(value = "/{paymentId}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PaymentResponse> patchPayment(@PathVariable long paymentId,
                                                        @RequestParam("status") PaymentStatus status) {
        return new ResponseEntity<>(new PaymentResponse(5), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "/{paymentId}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> deletePayment(@PathVariable long paymentId) {
        return ResponseEntity.noContent().build();
    }

    @Value
    private static class Receipt {
        private Amount price;

        private Amount discount;

        @JsonFormat(pattern = "dd::MM::yyyy HH:mm")
        LocalDateTime time;

        private PaymentStatus status;
    }

    @Value
    private class PaymentResponse {
        long id;
    }
}
