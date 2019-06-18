package com.kyriba.conference.discount.service;

import com.kyriba.conference.discount.api.dto.DiscountExternalDto;
import com.kyriba.conference.discount.api.dto.DiscountResponse;
import com.kyriba.conference.discount.api.dto.DiscountUpdateParamsDto;
import com.kyriba.conference.discount.dao.DiscountRepository;
import com.kyriba.conference.discount.domain.DiscountEntity;
import com.kyriba.conference.discount.domain.DiscountType;
import com.kyriba.conference.discount.util.DiscountDataMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Igor Lizura
 */
@Service
@Transactional
public class DiscountServiceImpl implements DiscountService{
    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public List<DiscountExternalDto> getAllDiscounts() {
        return discountRepository.findAll().stream().map(DiscountDataMapper.MAPPER::entityToDto).collect(Collectors.toList());
    }

    @Override
    public DiscountResponse createDiscount(DiscountExternalDto discountDto) {
        DiscountEntity entity = DiscountDataMapper.MAPPER.dtoToEntity(discountDto);
        return DiscountDataMapper.MAPPER.entityToResponse(discountRepository.save(entity));
    }

    @Override
    public DiscountExternalDto getDiscount(String type) {
        Optional<DiscountEntity> entity = discountRepository.findDiscountEntityByType(DiscountType.valueOf(type.toUpperCase()));
        return entity.map(DiscountDataMapper.MAPPER::entityToDto)
                .orElseThrow(() -> new NoSuchDiscountException(type));
    }

    @Override
    public void updateDiscount(String type, DiscountUpdateParamsDto params) {
        DiscountEntity entity = discountRepository.findDiscountEntityByType(DiscountType.valueOf(type.toUpperCase()))
                .orElseThrow(() -> new NoSuchDiscountException(type));
        DiscountDataMapper.MAPPER.updateDiscountEntityFromParams(params, entity);
        discountRepository.save(entity);
    }

    @Override
    public void deleteDiscount(String type) {
        discountRepository.deleteDiscountEntityByType(DiscountType.valueOf(type.toUpperCase()));
    }
}
