package org.levelup42.springwebdemo.product.infrastructure.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.levelup42.springwebdemo.common.mediator.Mediator;
import org.levelup42.springwebdemo.product.application.command.create.CreateProductRequest;
import org.levelup42.springwebdemo.product.application.command.delete.DeleteProductRequest;
import org.levelup42.springwebdemo.product.application.command.update.UpdateProductRequest;
import org.levelup42.springwebdemo.product.application.query.getAll.GetAllProductRequest;
import org.levelup42.springwebdemo.product.application.query.getAll.GetAllProductResponse;
import org.levelup42.springwebdemo.product.application.query.getById.GetProductByIdRequest;
import org.levelup42.springwebdemo.product.application.query.getById.GetProductByIdResponse;
import org.levelup42.springwebdemo.product.infrastructure.api.dto.CreateProductDto;
import org.levelup42.springwebdemo.product.infrastructure.api.dto.ProductDto;
import org.levelup42.springwebdemo.product.infrastructure.api.dto.UpdateProductDto;
import org.levelup42.springwebdemo.product.infrastructure.api.mapper.ProductMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController implements ProductApi {

    private final Mediator mediator;
    private final ProductMapper productMapper;

    // guardar
    @PostMapping("")
    public ResponseEntity<Void> saveProduct(@ModelAttribute @Valid CreateProductDto productDto) {

        log.info("Saving product {}", productDto);

        CreateProductRequest request = productMapper.mapToCreateProductRequest(productDto);

        mediator.dispach(request);

        log.info("Saved product {}", productDto);

        return ResponseEntity.created(URI.create("/api/v1/products/".concat(productDto.getId().toString()))).build();
    }

    // update
    @PutMapping("")
    public ResponseEntity<Void> updateProductById(@ModelAttribute @Valid UpdateProductDto productDto) {

        log.info("Updating product {}", productDto);

        UpdateProductRequest request= productMapper.mapToUpdateProductRequest(productDto);

        mediator.dispach(request);

        log.info("Updated product {}", productDto);

        return ResponseEntity.noContent().build();
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {

        log.info("Deleting product {}", id);

        mediator.dispachAsync(new DeleteProductRequest(id));

        log.info("Deleted product {}", id);

        return ResponseEntity.accepted().build();
    }

    // select all
    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProduct(@RequestParam(required = false) String pageSize) {

        log.info("Getting all products");

        GetAllProductResponse response =  mediator.dispach(new GetAllProductRequest());

        List<ProductDto> productDtos= response.getProducts().stream().map(productMapper:: mapToProduct).toList();

        log.info("Found {} products", productDtos.size());

        return ResponseEntity.ok(productDtos);
    }

    // select by id
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        log.info("Getting product by id: {}", id);

        GetProductByIdResponse response = mediator.dispach(new GetProductByIdRequest(id));

        ProductDto productDto = productMapper.mapToProduct(response.getProduct());

        log.info("Found product by id: {}", productDto.getId());

        return ResponseEntity.ok(productDto); // devuelve producto

    }


}
