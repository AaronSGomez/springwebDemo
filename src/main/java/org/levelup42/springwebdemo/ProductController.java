package org.levelup42.springwebdemo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")


public class ProductController {

    List<Product> products;

    public ProductController() {
        this.products = new ArrayList<>();
        products.add(Product.builder().id(1L).name("Product 1").description("Description 1").price(400.0).image("Category 1").build());
        products.add(Product.builder().id(2L).name("Product 2").description("Description 2").price(200.0).image("Category 1").build());
        products.add(Product.builder().id(3L).name("Product 3").description("Description 3").price(400.0).image("Category 3").build());
        // cada vez que arrancamos cargamos estos productos al array variable
    }

    // select all
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam(required = false) String pageSize){
        return ResponseEntity.ok(products);
    }

    // select by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Optional<Product> productOptional= products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        if(productOptional.isEmpty()){
            return ResponseEntity.notFound().build(); // error 404 sin datos
        }
            return ResponseEntity.ok(productOptional.get()); // devuelve producto

    }

    // guardar
    @PostMapping("")
    public ResponseEntity<Void> setProductById(@RequestBody Product product){
        products.add(product); // agregar a la lista el producto
        return ResponseEntity.created(URI.create("/api/v1/products/".concat(product.getId().toString()))).build();
    }

    // update
    @PutMapping("")
    public ResponseEntity<Product> updateProductById(@RequestBody Product product){
        Product productSelected = products.stream() // localiza el producto y lo modifica
                .filter(p -> p.getId().equals(product.getId()))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Product not found"));

        productSelected.setName(product.getName());
        productSelected.setDescription(product.getDescription());
        productSelected.setPrice(product.getPrice());
        productSelected.setImage(product.getImage());

        return ResponseEntity.ok(product);

    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id){
        // remove si el id es igual al pasado por parametros
        products.removeIf(p -> p.getId().equals(id));
        return ResponseEntity.noContent().build();
    }



}
