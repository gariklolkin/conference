package com.kyriba.conference.payment.service;

import com.kyriba.conference.payment.api.dto.PaymentDto;
import com.kyriba.conference.payment.api.dto.PaymentStatusUpdateDto;
import com.kyriba.conference.payment.api.dto.Receipt;
import com.kyriba.conference.payment.api.dto.TicketDto;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> findAll();

    Receipt createPayment(TicketDto ticketDto);

    PaymentDto getPayment(long paymentId);

    void updatePayment(long paymentId, PaymentStatusUpdateDto statusUpdateDto);

    void deletePayment(long paymentId);
}
