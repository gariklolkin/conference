package com.kyriba.conference.discount.dao;

import com.kyriba.conference.discount.domain.DiscountEntity;
import com.kyriba.conference.discount.domain.DiscountType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Igor Lizura
 */
public interface DiscountRepository extends CrudRepository<DiscountEntity, Long> {
    Optional<DiscountEntity> findByType(DiscountType type);

    List<DiscountEntity> findAll();

    void deleteByType(DiscountType type);
}
