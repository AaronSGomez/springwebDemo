package org.levelup42.springwebdemo.product.application.command.delete;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.levelup42.springwebdemo.common.mediator.Request;

@Data
@AllArgsConstructor
public class DeleteProductRequest implements Request<Void> {

    private Long id;
}
