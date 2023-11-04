package com.project.angleace.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateProductOrderRequest {
    private Integer product_id;
    private String name;
    private Integer amountS;
    private Integer amountM;
    private Integer amountL;
    private Integer amountXL;
    private Double cost;
    private Double sellPrice;
}


