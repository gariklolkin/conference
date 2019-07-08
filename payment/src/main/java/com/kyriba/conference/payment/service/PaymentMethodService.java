package com.kyriba.conference.payment.service;

import com.kyriba.conference.payment.api.dto.PaymentMethodDto;
import com.kyriba.conference.payment.api.dto.PaymentMethodResponse;
import com.kyriba.conference.payment.api.dto.PaymentMethodUrlUpdateDto;
import com.kyriba.conference.payment.domain.PaymentMethodType;

import java.util.List;

public interface PaymentMethodService {
    List<PaymentMethodDto> findAll();

    PaymentMethodResponse createPaymentMethod(PaymentMethodDto paymentMethodDto);

    PaymentMethodDto getPaymentMethod(PaymentMethodType type);

    void updatePaymentMethod(PaymentMethodType type, PaymentMethodUrlUpdateDto statusUpdateDto);

    void deletePaymentMethod(PaymentMethodType type);
}