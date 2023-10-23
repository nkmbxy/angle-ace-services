package com.project.angleace.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
@Data
@Accessors(chain = true)
public class CreateProductOrderRequest {
    private Integer product_id;
    private Integer amount;
    private String name;
    private LocalDate productOrderDate;
}
