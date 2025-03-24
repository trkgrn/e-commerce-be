package com.trkgrn.productservice.mapper;

import com.trkgrn.common.dto.productservice.response.ProductIndexDto;
import com.trkgrn.productservice.model.entity.VariantProduct;
import com.trkgrn.productservice.model.index.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductIndexMapper {
    ProductIndex toIndex(VariantProduct product);

    List<ProductIndexDto> toDto(List<ProductIndex> productIndices);
}
