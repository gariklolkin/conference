package com.kyriba.conference.payment.api;

import com.kyriba.conference.payment.api.dto.PaymentMethodDto;
import com.kyriba.conference.payment.api.dto.PaymentMethodResponse;
import com.kyriba.conference.payment.api.dto.PaymentMethodUrlUpdateDto;
import com.kyriba.conference.payment.domain.PaymentMethodType;
import com.kyriba.conference.payment.service.PaymentMethodService;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Garik Lizura
 */
@RestController
@RequestMapping("/api/v1/paymentMethods")
@RequiredArgsConstructor
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @ResponseBody
    @GetMapping
    List<PaymentMethodDto> getPaymentMethods() {
        return paymentMethodService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    PaymentMethodResponse createPaymentMethod(@ApiParam(value = "Payment method creation object", required = true)
                                                        @Valid @RequestBody PaymentMethodDto paymentMethod) {
        return paymentMethodService.createPaymentMethod(paymentMethod);
    }

    @GetMapping(value = "/{type}")
    PaymentMethodDto getPaymentMethod(@ApiParam(value = "Payment method type", required = true)
                                                 @PathVariable PaymentMethodType type) {
        return paymentMethodService.getPaymentMethod(type);
    }

    @PutMapping(value = "/{type}")
    void updatePaymentMethod(@ApiParam(value = "Payment method type", required = true) @PathVariable PaymentMethodType type,
                                    @ApiParam(value = "Payment method update parameters", required = true)
                                    @Valid @RequestBody PaymentMethodUrlUpdateDto urlUpdateDto) {
        paymentMethodService.updatePaymentMethod(type, urlUpdateDto);
    }

    @DeleteMapping(value = "/{type}")
    void deletePaymentMethod(@ApiParam(value = "Payment method type", required = true) @PathVariable PaymentMethodType type) {
        paymentMethodService.deletePaymentMethod(type);
    }
}
