package com.project.angleace.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateCustomerOrderRequest {
    private Integer amount;
    private String size;
}

