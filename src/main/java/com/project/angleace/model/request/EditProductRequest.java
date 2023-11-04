package com.project.angleace.model.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

@Data
@Accessors(chain = true)
public class EditProductRequest {
    private MultipartFile file;
    private String detail;
    private double sellPrice;
    private double cost;
}
