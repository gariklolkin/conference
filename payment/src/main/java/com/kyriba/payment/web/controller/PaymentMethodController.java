package com.kyriba.payment.web.controller;

import com.kyriba.payment.domain.PaymentMethodType;
import com.kyriba.payment.domain.dto.PaymentMethodDto;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.kyriba.payment.domain.PaymentMethodType.CREDIT_CARD;
import static com.kyriba.payment.domain.PaymentMethodType.WIRE_TRANSFER;

/**
 * @author Garik Lizura
 */
@RestController
@AllArgsConstructor
@RequestMapping("/v1/paymentMethod")
public class PaymentMethodController {
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethods() {
        PaymentMethodDto transfer = new PaymentMethodDto(WIRE_TRANSFER, "http://wrtransfer.com");
        PaymentMethodDto creditCard = new PaymentMethodDto(CREDIT_CARD,"https://webpay.by/en/");
        return new ResponseEntity<>(Arrays.asList(transfer, creditCard), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PaymentMethodResponse> createPaymentMethod(@RequestBody PaymentMethodDto paymentMethod) {
        return new ResponseEntity<>(new PaymentMethodResponse(CREDIT_CARD), HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping(value = "/{type}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PaymentMethodDto> getPaymentMethod(@PathVariable String type) {
        return new ResponseEntity<>(new PaymentMethodDto(WIRE_TRANSFER, "http://wrtransfer.com"), HttpStatus.OK);
    }

    @ResponseBody
    @PutMapping(value = "/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentMethodResponse> updatePaymentMethod(@PathVariable String type,
                                                                @RequestBody PaymentMethodDto paymentMethod) {
        return new ResponseEntity<>(new PaymentMethodResponse(CREDIT_CARD), HttpStatus.OK);
    }

    @PatchMapping(value = "/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentMethodResponse> patchPaymentMethod(@PathVariable String type,
                                                                    @RequestParam("url") String url) {
        return new ResponseEntity<>(new PaymentMethodResponse(WIRE_TRANSFER), HttpStatus.OK);
    }

    @ResponseBody
    @DeleteMapping(value = "/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentMethodDto> deletePaymentMethod(@PathVariable String type) {
        return ResponseEntity.noContent().build();
    }

    @Value
    private static class PaymentMethodResponse {
        private PaymentMethodType type;
    }
}
