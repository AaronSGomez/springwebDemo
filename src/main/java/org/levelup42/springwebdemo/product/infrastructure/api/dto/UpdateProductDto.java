package org.levelup42.springwebdemo.product.infrastructure.api.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateProductDto {

    private Long id;
    @NotBlank
    private String name;
    @Length(min= 10, max = 255, message= "Description must be between 10 and 255 characters")
    private String description;
    @DecimalMin(value ="0.01", inclusive = false)
    @DecimalMax(value ="999.99",  inclusive = false)
    private Double price;
    @Size(max=1024000)
    private MultipartFile file;
}
