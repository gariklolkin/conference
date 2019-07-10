package com.kyriba.conference.payment.service;

import com.kyriba.conference.payment.api.dto.*;
import com.kyriba.conference.payment.dao.PaymentRepository;
import com.kyriba.conference.payment.domain.dto.DiscountDto;
import com.kyriba.conference.payment.domain.PaymentEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Igor Lizura
 */
@Service
@AllArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final RestTemplate restTemplate;
    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentDto> findAll() {
        return paymentRepository.findAll().stream().map(PaymentEntity::toPaymentDto).collect(Collectors.toList());
    }

    @Override
    public Receipt createPayment(TicketDto ticketDto) {
        DiscountDto discount = restTemplate.getForObject(ticketDto.getDiscountType().name(), DiscountDto.class);
        PaymentEntity entity = new PaymentEntity(discount, ticketDto);
        return paymentRepository.save(entity).toReceipt();
    }

    @Override
    public PaymentDto getPayment(long paymentId) {
        Optional<PaymentEntity> entity = paymentRepository.findById(paymentId);
        return entity.map(PaymentEntity::toPaymentDto)
                .orElseThrow(() -> new NoSuchPaymentException("Payment", Long.toString(paymentId)));
    }

    @Override
    public void updatePayment(long paymentId, PaymentStatusUpdateDto statusUpdateDto) {
        PaymentEntity entity = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NoSuchPaymentException("Payment", String.valueOf(paymentId)));
        entity.setStatus(statusUpdateDto.getStatus());
    }

    @Override
    public void deletePayment(long paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}
