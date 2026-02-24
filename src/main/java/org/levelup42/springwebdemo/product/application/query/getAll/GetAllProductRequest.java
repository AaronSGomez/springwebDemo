package org.levelup42.springwebdemo.product.application.query.getById;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.levelup42.springwebdemo.common.mediator.Request;

@AllArgsConstructor
@Data
public class GetProductByIdRequest implements Request<GetProductByIdResponse> {

    private Long id;

}
