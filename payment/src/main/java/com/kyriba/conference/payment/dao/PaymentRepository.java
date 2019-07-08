package com.kyriba.conference.payment.dao;

import com.kyriba.conference.payment.domain.PaymentEntity;
import com.kyriba.conference.payment.domain.PaymentMethodType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends CrudRepository<PaymentEntity, Long> {
    List<PaymentEntity> findAll();

    Optional<PaymentEntity> findById(long paymentId);

    void deleteById(long paymentId);
}
