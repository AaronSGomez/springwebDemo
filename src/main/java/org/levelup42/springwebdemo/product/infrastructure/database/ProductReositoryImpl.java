package org.levelup42.springwebdemo.product.infrastructure.database;

import lombok.RequiredArgsConstructor;
import org.levelup42.springwebdemo.product.domain.Product;
import org.levelup42.springwebdemo.product.domain.ProductRepository;
import org.levelup42.springwebdemo.product.infrastructure.database.entity.ProductEntity;
import org.levelup42.springwebdemo.product.infrastructure.database.mapper.ProductEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductReositoryImpl implements ProductRepository {
    private final List<ProductEntity> products = new ArrayList<>();

    private final ProductEntityMapper productEntityMapper;

    @Override
    public void upsert(Product product) {
        ProductEntity productEntity = productEntityMapper.mapToProductEntity(product);
        products.removeIf(p->p.getId().equals(productEntity.getId()));
        products.add(productEntity);
    }

    @Override
    public Optional<Product> findById(long id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(productEntityMapper::maptoProduct);
    }

    @Override
    public List<Product> findAll() {
        return products.stream().map(productEntityMapper::maptoProduct).toList();
    }

    @Override
    public void deleteById(long id) {
        products.removeIf(products -> products.getId() == id);
    }
}
