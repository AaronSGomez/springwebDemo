package org.levelup42.springwebdemo.product.infrastructure.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.levelup42.springwebdemo.common.mediator.Mediator;
import org.levelup42.springwebdemo.product.application.query.getAll.GetAllProductRequest;
import org.levelup42.springwebdemo.product.application.query.getAll.GetAllProductResponse;
import org.levelup42.springwebdemo.product.domain.entity.Product;
import org.levelup42.springwebdemo.product.infrastructure.api.dto.ProductDto;
import org.levelup42.springwebdemo.product.infrastructure.api.mapper.ProductMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private Mediator mediator;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    @Test
    public void getAllProducts() {

        GetAllProductResponse getAllresponse = new GetAllProductResponse(List.of(
                Product.builder().id(1L).build(),
                Product.builder().id(2L).build(),
                Product.builder().id(3L).build(),
                Product.builder().id(4L).build(),
                Product.builder().id(5L).build()
        ));
        when(mediator.dispach(new GetAllProductRequest())).thenReturn(getAllresponse);

        ProductDto productDto = new ProductDto();

        productDto.setId(1L);

        when(productMapper.mapToProductDto(any(Product.class))).thenReturn(productDto);

        ResponseEntity<List<ProductDto>> response = productController.getAllProduct("5");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        List<ProductDto> productDtos = response.getBody();
        assertEquals(5, productDtos.size());
    }

}