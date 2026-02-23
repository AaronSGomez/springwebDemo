package org.levelup42.springwebdemo.product.infrastructure.database.mapper;


import org.levelup42.springwebdemo.product.domain.Product;
import org.levelup42.springwebdemo.product.infrastructure.database.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public class ProductEntityMapper {
    ProductEntity mapToProductEntity(Product product);
    Product maptoProduct(ProductEntity productEntity);

}
