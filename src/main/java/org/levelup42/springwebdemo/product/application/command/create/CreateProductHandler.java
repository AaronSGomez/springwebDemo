package org.levelup42.springwebdemo.product.application.command.create;

import lombok.RequiredArgsConstructor;
import org.levelup42.springwebdemo.common.mediator.RequestHandler;
import org.levelup42.springwebdemo.product.domain.Product;
import org.levelup42.springwebdemo.product.domain.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProductHandler implements RequestHandler<CreateProductRequest, Void> {

    private final ProductRepository productRepository;

    @Override
    public Void handle(CreateProductRequest request) {
        Product product = Product.builder()
                .id(1L)  // esto se modificará es una manera un poco sucia
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .image(request.getImage())
                .build();

        productRepository.upsert(product);
        return null;
    }

    @Override
    public Class<CreateProductRequest> getRequestType() {
        return CreateProductRequest.class;
    }
}
