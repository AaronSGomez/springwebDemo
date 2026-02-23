package org.levelup42.springwebdemo.product.application.query.getById;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.levelup42.springwebdemo.product.domain.Product;

@Builder
@Data
@AllArgsConstructor
public class GetProductByIdResponse {

    private Product product;

}
