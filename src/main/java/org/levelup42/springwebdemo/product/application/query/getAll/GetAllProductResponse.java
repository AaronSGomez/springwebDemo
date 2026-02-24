package org.levelup42.springwebdemo.product.application.query.getAll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.levelup42.springwebdemo.product.domain.Product;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class GetAllProductResponse {

    private List<Product> products;

}
