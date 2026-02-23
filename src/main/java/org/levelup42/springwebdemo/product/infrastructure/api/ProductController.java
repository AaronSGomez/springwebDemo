package org.levelup42.springwebdemo.product.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.levelup42.springwebdemo.common.mediator.Mediator;
import org.levelup42.springwebdemo.product.application.command.create.CreateProductRequest;
import org.levelup42.springwebdemo.product.application.query.getById.GetProductByIdRequest;
import org.levelup42.springwebdemo.product.application.query.getById.GetProductByIdResponse;
import org.levelup42.springwebdemo.product.infrastructure.api.dto.ProductDto;
import org.levelup42.springwebdemo.product.infrastructure.api.mapper.ProductMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor

public class ProductController implements ProductApi {

    private final Mediator mediator;
    private final ProductMapper productMapper;

    // guardar
    @PostMapping("")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductDto productDto) {
        CreateProductRequest request = productMapper.mapToCreateProductRequest(productDto);
        mediator.dispach(request);
        return ResponseEntity.created(URI.create("/api/v1/products/".concat(productDto.getId().toString()))).build();
    }

    // update
    @PutMapping("")
    public ResponseEntity<Void> updateProductById(@RequestBody ProductDto productDto) {
        return ResponseEntity.notFound().build();
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }

    // select all
    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProduct(@RequestParam(required = false) String pageSize) {
        return ResponseEntity.ok(null);
    }

    // select by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        GetProductByIdResponse response = mediator.dispach(new GetProductByIdRequest(id));
        ProductDto productDto = productMapper.mapToProduct(response.getProduct());
        return ResponseEntity.ok(productDto); // devuelve producto

    }


}
