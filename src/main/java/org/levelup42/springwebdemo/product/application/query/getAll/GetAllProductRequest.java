package org.levelup42.springwebdemo.product.application.query.getAll;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.levelup42.springwebdemo.common.mediator.Request;
import org.levelup42.springwebdemo.product.application.query.getById.GetProductByIdResponse;

@AllArgsConstructor
@Data
public class GetAllProductRequest implements Request<GetAllProductResponse> {

}
