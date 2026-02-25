package org.levelup42.springwebdemo.product.infrastructure.api.mapper;

import org.levelup42.springwebdemo.product.application.command.create.CreateProductRequest;
import org.levelup42.springwebdemo.product.application.command.update.UpdateProductRequest;
import org.levelup42.springwebdemo.product.domain.entity.Product;
import org.levelup42.springwebdemo.product.infrastructure.api.dto.CreateProductDto;
import org.levelup42.springwebdemo.product.infrastructure.api.dto.ProductDto;
import org.levelup42.springwebdemo.product.infrastructure.api.dto.UpdateProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    CreateProductRequest mapToCreateProductRequest(CreateProductDto createproductDto);
    UpdateProductRequest mapToUpdateProductRequest(UpdateProductDto updateproductDto);
    ProductDto mapToProductDto(Product product);



}
