package com.kyriba.conference.payment.dao;

import com.kyriba.conference.payment.domain.PaymentMethodEntity;
import com.kyriba.conference.payment.domain.PaymentMethodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {
    Optional<PaymentMethodEntity> findByType(PaymentMethodType type);

    void deleteByType(PaymentMethodType type);
}
