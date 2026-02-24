package org.levelup42.springwebdemo.common.util;

import org.jspecify.annotations.NonNull;
import org.levelup42.springwebdemo.product.application.command.update.UpdateProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileUtils {

    public String saveProductImage(MultipartFile file) {
        String uniquefilename;

        try(InputStream inputStream = file.getInputStream()){
            String filename= StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            uniquefilename = UUID.randomUUID().toString().concat("-").concat(filename);

            Path path = Path.of("uploads/products/");

            if(Files.exists(path)){
                Files.createDirectories(path);
            }
            Files.copy(inputStream,path.resolve(uniquefilename), StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            throw new RuntimeException("Error uploading file");
        }
        return uniquefilename;
    }
}
