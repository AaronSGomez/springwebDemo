package org.levelup42.springwebdemo.product.application.query.getById;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.levelup42.springwebdemo.common.mediator.RequestHandler;
import org.levelup42.springwebdemo.product.domain.entity.Product;
import org.levelup42.springwebdemo.product.domain.exception.ProductNotFoundException;
import org.levelup42.springwebdemo.product.domain.port.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetProductByIdHandler implements RequestHandler<GetProductByIdRequest, GetProductByIdResponse> {

    private final ProductRepository productRepository;


    @Override
    public GetProductByIdResponse handle(GetProductByIdRequest request) {

        log.info("Getting product by id {}", request.getId());

        Product product = productRepository.findById(request.getId()).
                orElseThrow(() -> new ProductNotFoundException(request.getId()));

        log.info("Found product {}", product);

        return new GetProductByIdResponse(product);
    }

    @Override
    public Class<GetProductByIdRequest> getRequestType() {
        return GetProductByIdRequest.class;
    }
}
