package org.levelup42.springwebdemo.product.application.command.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.levelup42.springwebdemo.common.mediator.Request;

@Data
@AllArgsConstructor
public class UpdateProductRequest implements Request<Void> {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;


}
