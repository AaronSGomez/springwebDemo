package org.levelup42.springwebdemo.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void upsert(Product product);
    Optional<Product> findById(long id);
    List<Product> findAll();
    void deleteById(long id);
}
