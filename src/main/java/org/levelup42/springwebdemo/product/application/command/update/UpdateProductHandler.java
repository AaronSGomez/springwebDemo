package org.levelup42.springwebdemo.product.application.command.update;

import ch.qos.logback.core.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.levelup42.springwebdemo.common.mediator.RequestHandler;
import org.levelup42.springwebdemo.common.util.FileUtils;
import org.levelup42.springwebdemo.product.domain.entity.Product;
import org.levelup42.springwebdemo.product.domain.port.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateProductHandler implements RequestHandler<UpdateProductRequest, Void> {

    private final ProductRepository productRepository;
    private final FileUtils fileUtils;


    @Override
    public Void handle(UpdateProductRequest request) {

        log.info("Updating product {}", request.getId());

        String uniquefilename = fileUtils.saveProductImage(request.getFile());

        Product product = Product.builder()
                .id(request.getId())  // esto se modificará es una manera un poco sucia
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .image(uniquefilename)
                .build();

        productRepository.upsert(product);

        log.info("Created product {}", product);

        return null;
    }



    @Override
    public Class<UpdateProductRequest> getRequestType() {
        return UpdateProductRequest.class;
    }
}
