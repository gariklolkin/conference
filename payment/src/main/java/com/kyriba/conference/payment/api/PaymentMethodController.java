package com.kyriba.conference.payment.api;

import com.kyriba.conference.payment.api.dto.PaymentMethodUpdateParamsDto;
import com.kyriba.conference.payment.domain.PaymentMethodType;
import com.kyriba.conference.payment.api.dto.PaymentMethodDto;
import io.swagger.annotations.ApiParam;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

import static com.kyriba.conference.payment.domain.PaymentMethodType.CREDIT_CARD;
import static com.kyriba.conference.payment.domain.PaymentMethodType.WIRE_TRANSFER;

/**
 * @author Garik Lizura
 */
@RestController
@RequestMapping("/api/v1/paymentMethods")
public class PaymentMethodController {
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping
    public List<PaymentMethodDto> getPaymentMethods() {
        PaymentMethodDto transfer = new PaymentMethodDto(WIRE_TRANSFER, "http://wrtransfer.com");
        PaymentMethodDto creditCard = new PaymentMethodDto(CREDIT_CARD,"https://webpay.by/en/");
        return Arrays.asList(transfer, creditCard);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PaymentMethodResponse createPaymentMethod(@ApiParam(value = "Payment method creation object", required = true)
                                                        @Valid @RequestBody PaymentMethodDto paymentMethod) {
        return new PaymentMethodResponse(CREDIT_CARD);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{type}")
    public PaymentMethodDto getPaymentMethod(@ApiParam(value = "Payment method type", required = true)
                                                 @PathVariable String type) {
        return new PaymentMethodDto(WIRE_TRANSFER, "http://wrtransfer.com");
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{type}")
    public void updatePaymentMethod(@ApiParam(value = "Payment method type", required = true) @PathVariable String type,
                                    @ApiParam(value = "Payment method update parameters", required = true)
                                    @Valid @RequestBody PaymentMethodUpdateParamsDto params) {
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{type}")
    public void deletePaymentMethod(@ApiParam(value = "Payment method type", required = true) @PathVariable String type) {
    }

    @Value
    private static class PaymentMethodResponse {
        private PaymentMethodType type;
    }
}
