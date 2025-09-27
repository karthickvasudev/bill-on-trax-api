package com.billontrax.modules.product.mappers;

import com.billontrax.modules.product.dtos.ProductCreateRequest;
import com.billontrax.modules.product.dtos.ProductDto;
import com.billontrax.modules.product.dtos.ProductUpdateRequest;
import com.billontrax.modules.product.entities.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductCreateRequest request);

    ProductDto toDto(Product entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductUpdateRequest request, @MappingTarget Product entity);
}

