package org.levelup42.springwebdemo.product.application.command.create;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.levelup42.springwebdemo.common.mediator.RequestHandler;
import org.levelup42.springwebdemo.common.util.FileUtils;
import org.levelup42.springwebdemo.product.domain.entity.Product;
import org.levelup42.springwebdemo.product.domain.port.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateProductHandler implements RequestHandler<CreateProductRequest, Void> {

    private final ProductRepository productRepository;
    private final FileUtils fileUtils;

    @Override
    public Void handle(CreateProductRequest request) {

        log.info("Creating product {}", request.getId());

        String uniquefilename = fileUtils.saveProductImage(request.getFile());

        Product product = Product.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .image(uniquefilename)
                .build();

        productRepository.upsert(product);

        log.info("Created product {}", product);

        return null;
    }

    @Override
    public Class<CreateProductRequest> getRequestType() {
        return CreateProductRequest.class;
    }
}
