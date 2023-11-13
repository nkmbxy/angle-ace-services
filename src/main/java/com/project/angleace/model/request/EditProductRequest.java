package com.project.angleace.model.request;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;

@Data
@Accessors(chain = true)
public class EditProductRequest {
    @Nullable
    private MultipartFile file;
    private String detail;
    private double sellPrice;
    private double cost;
}
