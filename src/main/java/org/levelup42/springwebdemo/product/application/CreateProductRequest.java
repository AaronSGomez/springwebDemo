package org.levelup42.springwebdemo.product.application;

import lombok.Data;
import org.levelup42.springwebdemo.common.mediator.Request;

@Data
public class CreateProductRequest implements Request<Void> {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String image;


}
