package org.levelup42.springwebdemo.product.infrastructure.api;

import org.levelup42.springwebdemo.product.domain.Product;
import org.levelup42.springwebdemo.product.infrastructure.api.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductApi {

    ResponseEntity<List<ProductDto>> getAllProduct(@RequestParam(required = false) String pageSize);
    ResponseEntity<ProductDto> getProductById(@PathVariable Long id);
    ResponseEntity<Void> saveProduct(@RequestBody ProductDto productDto);
    ResponseEntity<Void> updateProductById(@RequestBody ProductDto productDto);
    ResponseEntity<Void> deleteProductById(@PathVariable Long id);

}
