package com.trkgrn.productservice.mapper;

import com.trkgrn.common.dto.productservice.request.CreateVariantProductRequest;
import com.trkgrn.common.dto.productservice.request.UpdateVariantProductRequest;
import com.trkgrn.common.dto.productservice.response.VariantProductDto;
import com.trkgrn.productservice.model.entity.VariantProduct;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VariantProductMapper {
    VariantProductDto toDto(VariantProduct variantProduct);

    VariantProduct toEntity(CreateVariantProductRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VariantProduct toEntity(UpdateVariantProductRequest request, @MappingTarget VariantProduct variantProduct);
}
