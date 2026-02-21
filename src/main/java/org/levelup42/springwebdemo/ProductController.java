package org.levelup42.springwebdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")


public class ProductController {

    List<Product> products;

    public ProductController() {
        this.products = new ArrayList<>();
        products.add(Product.builder().id(1L).name("Product 1").description("Description 1").price(400.0).image("Category 1").build());
        products.add(Product.builder().id(2L).name("Product 2").description("Description 2").price(200.0).image("Category 1").build());
        products.add(Product.builder().id(3L).name("Product 3").description("Description 3").price(400.0).image("Category 3").build());

    }


    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam(required = false) String pageSize){
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Product product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Product not found"));

        return ResponseEntity.ok(product);
    }

    @PostMapping("")
    public ResponseEntity<Void> setProductById(@RequestBody Product product){
        products.add(product);
        return ResponseEntity.created(URI.create("/api/v1/products/".concat(product.getId().toString()))).build();
    }

    //meter metodos put y delete

}
