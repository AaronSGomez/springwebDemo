package org.levelup42.springwebdemo.product.application.query.getById;

import lombok.RequiredArgsConstructor;
import org.levelup42.springwebdemo.common.mediator.RequestHandler;
import org.levelup42.springwebdemo.product.domain.Product;
import org.levelup42.springwebdemo.product.domain.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProductByIdHandler implements RequestHandler<GetProductByIdRequest, GetProductByIdResponse> {

    private final ProductRepository productRepository;


    @Override
    public GetProductByIdResponse handle(GetProductByIdRequest request) {
        Product product = productRepository.findById(request.getId()).
                orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return new GetProductByIdResponse(product);
    }

    @Override
    public Class<GetProductByIdRequest> getRequestType() {
        return GetProductByIdRequest.class;
    }
}
