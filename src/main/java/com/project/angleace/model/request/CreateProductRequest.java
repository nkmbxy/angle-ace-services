package com.project.angleace.model.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@Accessors(chain = true)
public class CreateProductRequest {
    private MultipartFile file;
    private String code;
    private String name;
    private String type;
    private String manufacturer;
    private String detail;
    private String size;
    private double sellPrice;
    private double cost;
}