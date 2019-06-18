package com.kyriba.conference.discount;

import com.kyriba.conference.discount.api.dto.DiscountExternalDto;
import com.kyriba.conference.discount.api.dto.DiscountResponse;
import com.kyriba.conference.discount.api.dto.DiscountUpdateParamsDto;
import com.kyriba.conference.discount.domain.DiscountEntity;
import com.kyriba.conference.discount.domain.DiscountType;
import com.kyriba.conference.discount.util.DiscountDataMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Igor Lizura
 */
public class DiscountDataMapperTest {
    @Test
    public void entityToResponse() {
        DiscountEntity discountEntity = new DiscountEntity(1L, DiscountType.STUDENT, 30);
        DiscountResponse discountResponse = DiscountDataMapper.MAPPER.entityToResponse( discountEntity );
        assertEquals( DiscountType.STUDENT, discountResponse.getType() );
    }

    @Test
    public void entityToDto() {
        DiscountEntity discountEntity = new DiscountEntity(1L, DiscountType.STUDENT, 30);
        DiscountExternalDto discountInputDto = DiscountDataMapper.MAPPER.entityToDto(discountEntity);
        assertEquals( DiscountType.STUDENT, discountInputDto.getType() );
        assertEquals( 30, discountInputDto.getPercentage());
    }

    @Test
    public void dtoToEntity() {
        DiscountExternalDto discountInputDto = new DiscountExternalDto(DiscountType.STUDENT, 30);
        DiscountEntity discountEntity = DiscountDataMapper.MAPPER.dtoToEntity(discountInputDto);
        assertEquals( DiscountType.STUDENT, discountEntity.getType() );
        assertEquals( 30, discountEntity.getPercentage());
    }

    @Test
    public void paramsToEntity() {
        DiscountUpdateParamsDto paramsDto = new DiscountUpdateParamsDto(70);
        DiscountEntity discountEntity = DiscountDataMapper.MAPPER.paramsToDto(paramsDto);
        assertEquals( 70, discountEntity.getPercentage());
    }

    @Test
    public void updateDiscountEntityFromParams() {
        DiscountUpdateParamsDto paramsDto = new DiscountUpdateParamsDto(70);
        DiscountEntity discountEntity = new DiscountEntity(3L, DiscountType.STUDENT, 50);
        DiscountDataMapper.MAPPER.updateDiscountEntityFromParams(paramsDto, discountEntity);
        assertEquals( 70, discountEntity.getPercentage());
        assertEquals( DiscountType.STUDENT, discountEntity.getType());
    }
}
