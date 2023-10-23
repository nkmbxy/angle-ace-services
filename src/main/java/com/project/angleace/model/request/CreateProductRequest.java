package com.project.angleace.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateProductRequest {
    private String name;
    private String type;
    private String manufacturer;
    private String pathImage;
    private String detail;
    private String size;
    private double sellPrice;
    private double cost;
}