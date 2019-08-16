package com.kyriba.conference.payment.dao;

import com.kyriba.conference.payment.domain.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}