package org.levelup42.springwebdemo.product.application.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.levelup42.springwebdemo.product.domain.port.ProductRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixProductsPriceSchedule {

    private final ProductRepository productRepository;

    @Scheduled(fixedRate = 60000)
    public void fixProductsPrice(){

        log.info("Fixing products price schedule");

        productRepository.findAll().forEach(product -> {
            product.setPrice(product.getPrice()*1.1);
            productRepository.upsert(product);
        });

        log.info("Fixed products price schedule complete");

    }
}
