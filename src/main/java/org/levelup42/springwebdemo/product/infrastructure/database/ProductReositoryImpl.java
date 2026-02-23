package org.levelup42.springwebdemo.product.infrastructure.database;
import org.levelup42.springwebdemo.product.domain.Product;
import org.levelup42.springwebdemo.product.domain.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductReositoryImpl implements ProductRepository {
    private final List<Product> products;

    public ProductReositoryImpl() {
        this.products = new ArrayList<>();
    }

    @Override
    public void upsert(Product product) {
        products.add(product);
    }

    @Override
    public Optional<Product> findById(long id) {
        return products.stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public void deleteById(long id) {
        products.removeIf(products -> products.getId() == id);
    }
}
