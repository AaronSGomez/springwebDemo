package org.levelup42.springwebdemo.product.application.command.delete;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.levelup42.springwebdemo.common.mediator.RequestHandler;
import org.levelup42.springwebdemo.product.domain.port.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteProductHandler implements RequestHandler<DeleteProductRequest, Void> {

    private final ProductRepository productRepository;

    @Override
    public Void handle(DeleteProductRequest request) {

        log.info("Deleting product {}", request.getId());

        productRepository.deleteById(request.getId());

        log.info("Deleted product {}", request.getId());

        return null;
    }

    @Override
    public Class<DeleteProductRequest> getRequestType() {
        return DeleteProductRequest.class;
    }
}
