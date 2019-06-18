package com.kyriba.conference.discount.dao;

import com.kyriba.conference.discount.domain.DiscountEntity;
import com.kyriba.conference.discount.domain.DiscountType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Igor Lizura
 */
@Repository
public interface DiscountRepository extends CrudRepository<DiscountEntity, Long> {
    Optional<DiscountEntity> findDiscountEntityByType(DiscountType type);

    List<DiscountEntity> findAll();

    void deleteDiscountEntityByType(DiscountType type);
}
