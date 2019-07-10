package com.kyriba.conference.payment.service;

import com.kyriba.conference.payment.api.dto.PaymentMethodDto;
import com.kyriba.conference.payment.api.dto.PaymentMethodResponse;
import com.kyriba.conference.payment.api.dto.PaymentMethodUrlUpdateDto;
import com.kyriba.conference.payment.dao.PaymentMethodRepository;
import com.kyriba.conference.payment.domain.PaymentMethodEntity;
import com.kyriba.conference.payment.domain.PaymentMethodType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    @Override
    public List<PaymentMethodDto> findAll() {
        return paymentMethodRepository.findAll().stream()
                .map(PaymentMethodEntity::toPaymentMethodDto).collect(Collectors.toList());
    }

    @Override
    public PaymentMethodResponse createPaymentMethod(PaymentMethodDto paymentMethodDto) {
        PaymentMethodEntity entity = new PaymentMethodEntity(paymentMethodDto);
        return paymentMethodRepository.save(entity).toPaymentMethodResponse();
    }

    @Override
    public PaymentMethodDto getPaymentMethod(PaymentMethodType type) {
        Optional<PaymentMethodEntity> entity = paymentMethodRepository.findByType(type);
        return entity.map(PaymentMethodEntity::toPaymentMethodDto)
                .orElseThrow(() -> new NoSuchPaymentException("Payment method", type.name()));
    }

    @Override
    public void updatePaymentMethod(PaymentMethodType type, PaymentMethodUrlUpdateDto urlUpdateDto) {
        PaymentMethodEntity entity = paymentMethodRepository.findByType(type)
                .orElseThrow(() -> new NoSuchPaymentException("Payment method", type.name()));
        entity.setUrl(urlUpdateDto.getUrl());
    }

    @Override
    public void deletePaymentMethod(PaymentMethodType type) {
        paymentMethodRepository.deleteByType(type);
    }
}
