package com.project.angleace.model.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RemoveProductRequest {
    private List<Integer> productsID;
}
