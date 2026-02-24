package org.levelup42.springwebdemo.product.application.command.delete;

import lombok.RequiredArgsConstructor;
import org.levelup42.springwebdemo.common.mediator.RequestHandler;
import org.levelup42.springwebdemo.product.application.command.create.CreateProductRequest;
import org.levelup42.springwebdemo.product.domain.Product;
import org.levelup42.springwebdemo.product.domain.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProductHandler implements RequestHandler<DeleteProductRequest, Void> {

    private final ProductRepository productRepository;

    @Override
    public Void handle(DeleteProductRequest request) {
        productRepository.deleteById(request.getId());
        return null;
    }

    @Override
    public Class<DeleteProductRequest> getRequestType() {
        return DeleteProductRequest.class;
    }
}
