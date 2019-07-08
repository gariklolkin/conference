package com.kyriba.conference.payment.api;

import com.kyriba.conference.payment.api.dto.PaymentDto;
import com.kyriba.conference.payment.api.dto.PaymentStatusUpdateDto;
import com.kyriba.conference.payment.api.dto.Receipt;
import com.kyriba.conference.payment.api.dto.TicketDto;
import com.kyriba.conference.payment.service.PaymentService;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


/**
 * @author Igor Lizura
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Valid List<PaymentDto> getPayments() {
        return paymentService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Valid Receipt createPayment(@ApiParam(value = "Payment creation object", required = true)
                                     @Valid @RequestBody TicketDto ticket) {
        return paymentService.createPayment(ticket);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{paymentId}", produces = APPLICATION_JSON_UTF8_VALUE)
    @Valid PaymentDto getPayment(@ApiParam(value = "Payment id", required = true) @PathVariable long paymentId) {
        return paymentService.getPayment(paymentId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{paymentId}")
    void updatePayment(@ApiParam(value = "Payment id", required = true) @PathVariable long paymentId,
                              @ApiParam(value = "Payment updating parameters", required = true)
                              @Valid @RequestBody PaymentStatusUpdateDto statusUpdateDto) {
        paymentService.updatePayment(paymentId, statusUpdateDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{paymentId}")
    void deletePayment(@ApiParam(value = "Payment id", required = true) @PathVariable long paymentId) {
        paymentService.deletePayment(paymentId);
    }
}
