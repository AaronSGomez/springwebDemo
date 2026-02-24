package org.levelup42.springwebdemo.product.application.command.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.levelup42.springwebdemo.common.mediator.Request;

@Data
@AllArgsConstructor
public class CreateProductRequest implements Request<Void> {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;


}
