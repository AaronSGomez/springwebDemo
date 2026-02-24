package org.levelup42.springwebdemo.product.application.query.getAll;

import lombok.RequiredArgsConstructor;
import org.levelup42.springwebdemo.common.mediator.RequestHandler;
import org.levelup42.springwebdemo.product.domain.entity.Product;
import org.levelup42.springwebdemo.product.domain.port.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllProductHandler implements RequestHandler<GetAllProductRequest, GetAllProductResponse> {

    private final ProductRepository productRepository;


    @Override
    public GetAllProductResponse handle(GetAllProductRequest request) {
        List<Product> products = productRepository.findAll();
        return new GetAllProductResponse(products);
    }

    @Override
    public Class<GetAllProductRequest> getRequestType() {
        return GetAllProductRequest.class;
    }

}

