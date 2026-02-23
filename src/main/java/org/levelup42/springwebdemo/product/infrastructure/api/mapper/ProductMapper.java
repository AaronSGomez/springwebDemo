package org.levelup42.springwebdemo.product.infrastructure.api.mapper;

import org.levelup42.springwebdemo.product.application.CreateProductRequest;
import org.levelup42.springwebdemo.product.infrastructure.api.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    CreateProductRequest mapToCreateProductRequest(ProductDto productDto);
}
