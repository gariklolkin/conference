package com.kyriba.conference.discount.service;

import com.kyriba.conference.discount.api.dto.DiscountDto;
import com.kyriba.conference.discount.api.dto.DiscountResponse;
import com.kyriba.conference.discount.api.dto.DiscountPercentageUpdateDto;
import com.kyriba.conference.discount.dao.DiscountRepository;
import com.kyriba.conference.discount.domain.DiscountEntity;
import com.kyriba.conference.discount.domain.DiscountType;
import com.kyriba.conference.discount.util.DiscountDataMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
public class DiscountServiceImpl implements DiscountService{
    private final DiscountRepository discountRepository;

    @Override
    public List<DiscountDto> getAllDiscounts() {
        return discountRepository.findAll().stream().map(DiscountDataMapper.MAPPER::entityToDto).collect(Collectors.toList());
    }

    @Override
    public DiscountResponse createDiscount(DiscountDto discountDto) {
        DiscountEntity entity = DiscountDataMapper.MAPPER.dtoToEntity(discountDto);
        return DiscountDataMapper.MAPPER.entityToResponse(discountRepository.save(entity));
    }

    @Override
    public DiscountDto getDiscount(DiscountType type) {
        Optional<DiscountEntity> entity = discountRepository.findByType(type);
        return entity.map(DiscountDataMapper.MAPPER::entityToDto)
                .orElseThrow(() -> new NoSuchDiscountException(type.name()));
    }

    @Override
    public void updateDiscount(DiscountType type, DiscountPercentageUpdateDto params) {
        DiscountEntity entity = discountRepository.findByType(type)
                .orElseThrow(() -> new NoSuchDiscountException(type.name()));
        DiscountDataMapper.MAPPER.updateDiscountEntityFromParams(params, entity);
        discountRepository.save(entity);
    }

    @Override
    public void deleteDiscount(DiscountType type) {
        discountRepository.deleteByType(type);
    }
}
