package com.trkgrn.productservice.mapper;

import com.trkgrn.common.dto.productservice.request.CreateProductRequest;
import com.trkgrn.common.dto.productservice.request.UpdateProductRequest;
import com.trkgrn.common.dto.productservice.response.ProductDto;
import com.trkgrn.productservice.model.entity.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductDto toDto(Product product);

    Product toEntity(CreateProductRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateEntity(UpdateProductRequest request, @MappingTarget Product product);
}
