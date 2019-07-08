package com.kyriba.conference.payment.dao;

import com.kyriba.conference.payment.domain.PaymentMethodEntity;
import com.kyriba.conference.payment.domain.PaymentMethodType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethodEntity, Long> {
    List<PaymentMethodEntity> findAll();

    Optional<PaymentMethodEntity> findByType(PaymentMethodType type);

    void deleteByType(PaymentMethodType type);
}
