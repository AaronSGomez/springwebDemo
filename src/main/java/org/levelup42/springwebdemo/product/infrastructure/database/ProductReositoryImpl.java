package org.levelup42.springwebdemo.product.infrastructure.database;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.levelup42.springwebdemo.product.domain.entity.Product;
import org.levelup42.springwebdemo.product.domain.port.ProductRepository;
import org.levelup42.springwebdemo.product.infrastructure.database.entity.ProductEntity;
import org.levelup42.springwebdemo.product.infrastructure.database.mapper.ProductEntityMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductReositoryImpl implements ProductRepository {
    private final List<ProductEntity> products = new ArrayList<>();

    private final ProductEntityMapper productEntityMapper;

    @Override
    public void upsert(Product product) {
        ProductEntity productEntity = productEntityMapper.mapToProductEntity(product);
        products.removeIf(p->p.getId().equals(productEntity.getId()));
        products.add(productEntity);
    }

    @Cacheable(value= "products", key="#id") // guardamos en cache para agiilizar busquedas
    @Override
    public Optional<Product> findById(long id) {
        log.info("Finding product by id {}", id);
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .map(productEntityMapper::maptoProduct);
    }

    @Override
    public List<Product> findAll() {
        return products.stream().map(productEntityMapper::maptoProduct).toList();
    }

    @CacheEvict(value="products" , key="#id") //en el caso de eliminacion la borras de cache
    @Override
    public void deleteById(long id) {
        products.removeIf(products -> products.getId() == id);
    }
}
